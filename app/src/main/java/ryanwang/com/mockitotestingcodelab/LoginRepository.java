package ryanwang.com.mockitotestingcodelab;

public interface LoginRepository {
	void login(String account, String password, Callback callback);

	interface Callback {
		void onSuccess();

		void onFail();
	}
}
