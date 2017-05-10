package ryanwang.com.mockitotestingcodelab;

import android.os.Handler;

public class LoginRepositoryImpl implements LoginRepository {
	@Override
	public void login(String account, String password, final Callback callback) {
		if (!account.isEmpty() && !password.isEmpty()) {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					callback.onSuccess();
				}
			},3000);
		} else {
			callback.onFail();
		}
	}
}
