package vcoty.vainglory.go.ui;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.support.design.widget.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;
import com.chad.library.adapter.base.*;
import com.jakewharton.rxbinding2.view.*;
import com.jakewharton.rxbinding2.widget.*;
import com.trello.rxlifecycle2.android.*;
import java.util.*;
import java.util.concurrent.*;
import org.litepal.crud.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.adapter.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.db.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.utils.*;
import vcoty.vainglory.go.widget.*;

public class SearchBarActivity extends BaseActivity {

	// UI
	private TextView mGoTv;
	private EditText mEditText;
	private RecyclerView mRecyclerView;

	private int duration;

	private String name;

	private boolean undoFlag = true;

	@Override
	protected void initial() {
		initView();
		animationIn();
	}

	private void initView() {
		mGoTv = (TextView) findViewById(R.id.tv_search_bar_activity_go);
		RxView.clicks(mGoTv)
			.throttleFirst(600, TimeUnit.MILLISECONDS)
			.compose(this.<Object>bindUntilEvent(ActivityEvent.DESTROY))
			.subscribe(new io.reactivex.functions.Consumer<Object>(){

				@Override
				public void accept(Object p1) throws Exception {
					startSearch();
				}
			});

		//建议列表配置
		mRecyclerView = (RecyclerView) findViewById(R.id.rv_search_bar_activity_suggestion_list);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		mRecyclerView.addItemDecoration(new NormalItemDecoration.Builder(this)
										.colorResId(AttrResourceUtil.getAttrResId(this, R.attr.colorDivider))
										.sizeResId(R.dimen.divider_size)
										.marginResId(R.dimen.divider_margin_left, R.dimen.divider_margin_right)
										.build());

		// 建议列表 //
		configSuggestionList();

		//搜索键监听
		RxView.clicks(mGoTv)
			.throttleFirst(600, TimeUnit.MILLISECONDS)
			.compose(this.<Object>bindUntilEvent(ActivityEvent.DESTROY))
			.subscribe(new io.reactivex.functions.Consumer<Object>(){

				@Override
				public void accept(Object p1) throws Exception {
					startSearch();
				}
			});


		mEditText = (EditText) findViewById(R.id.et_search_bar_activity);
		mEditText.setFocusable(true);
		mEditText.setFocusableInTouchMode(true);
		mEditText.requestFocus();

		//回车监听
		mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {  

				@Override  
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {  
					if (actionId == EditorInfo.IME_ACTION_SEND) { 
						startSearch();
					}  
					return false;  
				}  
			});  

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private void configSuggestionList() {
		mEditText = (EditText) findViewById(R.id.et_search_bar_activity);
		final SearchSuggestionsAdapter adapter = new SearchSuggestionsAdapter(SearchBarActivity.this, new ArrayList<SuggestionPlayer>());
		mRecyclerView.setAdapter(adapter);
		adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){

				@Override
				public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
					name =  ((SuggestionPlayer) adapter.getData().get(position)).getName();
					startSearch();
				}
			});
		adapter.openLoadAnimation();
		final View footView = LayoutInflater.from(SearchBarActivity.this).inflate(R.layout.view_foot_suggestion, mRecyclerView, false);

		adapter.addFooterView(footView);
		final List<SuggestionPlayer> player = DataSupport.findAll(SuggestionPlayer.class);

		//清除所有建议
		RxView.clicks(footView)
			.throttleFirst(600, TimeUnit.MILLISECONDS)
			.compose(SearchBarActivity.this.bindUntilEvent(ActivityEvent.DESTROY))
			.subscribe(new io.reactivex.functions.Consumer<Object>(){

				@Override
				public void accept(Object p1) throws Exception {
					undoFlag = false;
					adapter.replaceData(new ArrayList<SuggestionPlayer>());

					for (SuggestionPlayer dbPlayer : player) {
						dbPlayer.delete();
					}

					Snackbar.make(mRecyclerView, R.string.search_activity_delete_all_data_snack_bar, Snackbar.LENGTH_LONG)
						.setAction(R.string.search_activity_delete_all_data_snack_bar_action, new View.OnClickListener(){

							@Override
							public void onClick(View p1) {
								undoFlag = true;
								adapter.openLoadAnimation();
								adapter.addData(player);

								for (SuggestionPlayer dbplayer : player) {
									dbplayer.save();
								}
							}
						}).show();
				}
			});

		//建议列表
		RxTextView.textChanges(mEditText)
			.compose(this.<CharSequence>bindUntilEvent(ActivityEvent.DESTROY))
			.subscribe(new io.reactivex.functions.Consumer<CharSequence>(){
				@Override
				public void accept(CharSequence p1) throws Exception {

					List<SuggestionPlayer> suggestions = DataSupport.where("name like ?", p1 + "%").find(SuggestionPlayer.class);

					if (suggestions != null && !suggestions.isEmpty()) {
						//倒序排序	
						Collections.reverse(suggestions);

						adapter.replaceData(suggestions);

						if (!footView.isShown()) {
							footView.setVisibility(View.VISIBLE);
						}

					} else {
						adapter.replaceData(new ArrayList<SuggestionPlayer>());
						if (footView.isShown()) {
							footView.setVisibility(View.INVISIBLE);
						}
					}
				}
			});

		//回车监听
		mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {  

				@Override  
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {  
					if (actionId == EditorInfo.IME_ACTION_SEND) {  
						startSearch();
					}  
					return false;  
				}  
			});  
	}

	public void startSearch() {
		if (name == null) {
			name = mEditText.getText().toString();
		}

		if ("".equals(name)) {
			Snackbar.make(mRecyclerView, R.string.search_activity_editor_cant_null, Snackbar.LENGTH_SHORT).show();
		} else if (!NetTaskUtil.isNetworkConnected(this)) {
			Snackbar.make(mRecyclerView, R.string.search_activity_error_no_network, Snackbar.LENGTH_SHORT).show();
		} else {
			Intent mIntent = new Intent(SearchBarActivity.this, MainActivity.class);
			String region = getPreference().getString(PreferenceKey.SETTING_REGIONS, "cn");

			if (region == null) {
				region = Region.CHINA.value();		
			}

			new SuggestionPlayerSaveThread(name).start();

			mIntent.putExtra(ExtraKey.EXTRA_KEY_PLAYER_NAME, name);
			mIntent.putExtra(ExtraKey.EXTRA_KEY_REGION, getPreference().getString(PreferenceKey.SETTING_REGIONS, Region.CHINA.value()));
			mIntent.putExtra(ExtraKey.EXTRA_KEY_BOOLEAN_1, false);

			View firstView = (RelativeLayout) findViewById(R.id.rl_search_bar_activity);
			startActivity(mIntent, ActivityOptions.makeSceneTransitionAnimation(SearchBarActivity.this, firstView, "sharedView").toBundle());	
		}
	}

	//进入时动画
	private void animationIn() {
		ObjectAnimator.ofFloat(mGoTv, "alpha", 0f, 1f)
			.setDuration(duration)
			.start();

		ObjectAnimator.ofFloat(mEditText, "alpha", 0f, 1f)
			.setDuration(duration)
			.start();

		ObjectAnimator.ofFloat(mRecyclerView, "alpha", 0f, 1f)
			.setDuration(duration + 400)
			.start();
	}

	//返回键监听
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		ObjectAnimator.ofFloat(mGoTv, "alpha", 1f, 0f)
			.setDuration(duration)
			.start();

		ObjectAnimator.ofFloat(mEditText, "alpha", 1f, 0f)
			.setDuration(duration)
			.start();

		ObjectAnimator.ofFloat(mRecyclerView, "alpha", 1f, 0f)
			.setDuration(duration)
			.start();

	}

	@Override
	protected int getLayoutResId() {
		return R.layout.search_bar_activity;
	}
}
