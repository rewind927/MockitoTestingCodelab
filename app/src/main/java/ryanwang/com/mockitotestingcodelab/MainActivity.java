package ryanwang.com.mockitotestingcodelab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoginView {

	private EditText editTextAccount;
	private EditText editTextPassword;
	private ProgressBar progressbarLoading;
	private Button buttonLogin;

	private LoginPresenter loginPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		loginPresenter = new LoginPresenterImpl(this, new LoginRepositoryImpl(), new NetworkManager(this));
	}

	private void initView() {
		editTextAccount = (EditText) findViewById(R.id.input_account);
		editTextPassword = (EditText) findViewById(R.id.input_password);
		progressbarLoading = (ProgressBar) findViewById(R.id.progressbar_loading);
		buttonLogin = (Button) findViewById(R.id.btn_login);
		buttonLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loginPresenter.login(editTextAccount.getText().toString(), editTextPassword.getText().toString());
			}
		});
	}

	@Override
	public void showLoading() {
		progressbarLoading.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideLoading() {
		progressbarLoading.setVisibility(View.GONE);
	}

	@Override
	public void showSuccessMessage(String message) {
		Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
	}

	@Override
	public void showFailed(String message) {
		Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void showInputDataIsEmpty() {
		Toast.makeText(MainActivity.this, "Input String is empty, please fill up before you press login!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void showNetworkError() {
		Toast.makeText(MainActivity.this, "Input String is empty, please fill up before you press login!", Toast.LENGTH_SHORT).show();
	}

}
