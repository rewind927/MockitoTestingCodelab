package ryanwang.com.mockitotestingcodelab;

public interface LoginView {
	void showLoading();

	void hideLoading();

	void showSuccessMessage(String message);

	void showFailed(String message);

	void showInputDataIsEmpty();

	void showNetworkError();
}
