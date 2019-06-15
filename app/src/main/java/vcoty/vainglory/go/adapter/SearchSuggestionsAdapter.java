package vcoty.vainglory.go.adapter;

import android.graphics.*;
import android.widget.*;
import com.chad.library.adapter.base.*;
import com.jakewharton.rxbinding2.view.*;
import com.trello.rxlifecycle2.android.*;
import java.util.*;
import java.util.concurrent.*;
import org.litepal.crud.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.db.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.widget.*;

import vcoty.vainglory.go.enums.Region;

public class SearchSuggestionsAdapter extends BaseQuickAdapter<SuggestionPlayer,BaseViewHolder> {
	private BaseActivity ctx;

	public SearchSuggestionsAdapter(BaseActivity ctx, List<SuggestionPlayer> datas) {
		super(R.layout.item_suggestion, datas);
		this.ctx = ctx;
	}

	@Override
	protected void convert(final BaseViewHolder holder, final SuggestionPlayer player) {
		if (player != null) {
			holder.setText(R.id.tv_suggestion_item, player.getName());

			ImageView mImageView = holder.getView(R.id.iv_suggestion_item_delete);

			//配置头像
			CircleTextImageView circleText = holder.getView(R.id.circle_text_iv_suggestion_item_head_img);
			circleText.setText(player.getRegion().toUpperCase());
			circleText.setFillColorResource(Region.getColorResID(Region.parse(player.getRegion())));
			circleText.setTextColor(Color.WHITE);
			RxView.clicks(mImageView)
				.compose(ctx.<Object>bindUntilEvent(ActivityEvent.DESTROY))
				.throttleFirst(600, TimeUnit.MILLISECONDS)
				.subscribe(new io.reactivex.functions.Consumer<Object>(){
					@Override
					public void accept(Object p1) throws Exception {
						DataSupport.deleteAll(SuggestionPlayer.class, "name = ?", player.getName());
						SearchSuggestionsAdapter.this.remove(holder.getPosition());
					}
				});
		}
	}
}

