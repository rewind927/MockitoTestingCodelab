package ryanwang.com.mockitotestingcodelab;

public class LoginPresenterImpl implements LoginPresenter {

	private LoginView loginView;
	private LoginRepository loginRepository;
	private NetworkManager networkManager;

	public LoginPresenterImpl(LoginView loginView, LoginRepository loginRepository, NetworkManager networkManager) {
		this.loginView = loginView;
		this.loginRepository = loginRepository;
		this.networkManager = networkManager;
	}

	@Override
	public void login(String account, String password) {
		loginView.showLoading();
		if (!networkManager.isNetworkAvailable()) {
			loginView.hideLoading();
			loginView.showNetworkError();
			return;
		}
		if (account == null || password == null || account.isEmpty() || password.isEmpty()) {
			loginView.hideLoading();
			loginView.showInputDataIsEmpty();
		} else {
			loginRepository.login(account, password, new LoginRepository.Callback() {
				@Override
				public void onSuccess() {
					loginView.hideLoading();
					loginView.showSuccessMessage("login success");
				}

				@Override
				public void onFail() {
					loginView.hideLoading();
					loginView.showFailed("login failed");
				}
			});
		}
	}
}
