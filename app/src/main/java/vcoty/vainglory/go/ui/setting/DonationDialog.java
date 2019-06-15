package vcoty.vainglory.go.ui.setting;
import android.app.*;
import android.content.*;
import android.view.*;
import vcoty.vainglory.go.*;
import android.widget.*;

public class DonationDialog extends Dialog {
	
	private View.OnClickListener mListener;
	
	private View view;
	
	public DonationDialog(Context ctx,View.OnClickListener mlistener) {
        super(ctx, R.style.DonationDialog);
		this.mListener = mlistener;
		this.view = View.inflate(ctx,R.layout.view_donation_dialog,null);
		setContentView(view);
		init(ctx);
    }

	private void init(Context ctx) {
		if(view != null && ctx != null){
			Button btnAlipay = view.findViewById(R.id.btn_view_donation_dialog);
			btnAlipay.setOnClickListener(mListener);
		}
	}
	
	
}
