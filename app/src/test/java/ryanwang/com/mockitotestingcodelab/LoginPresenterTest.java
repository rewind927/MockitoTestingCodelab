package ryanwang.com.mockitotestingcodelab;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginPresenterTest {

	@Mock
	private LoginView loginView;
	@Mock
	private LoginRepositoryImpl loginRepository;
	@Mock
	private NetworkManager networkManager;

//	@Rule
//	public MockitoRule rule = MockitoJUnit.rule();

	@InjectMocks
	private LoginPresenterImpl loginPresenter;

	@Captor
	private ArgumentCaptor<LoginRepository.Callback> callbackCaptor;

	@Before
	public void setupPresenter() {
		MockitoAnnotations.initMocks(this);
//		loginPresenter = new LoginPresenterImpl(loginView, loginRepository, networkManager);
	}

	@Test
	public void login_success_showSuccessMessage() {
		LoginView loginView = mock(LoginView.class);
		LoginRepositoryImpl loginRepository = mock(LoginRepositoryImpl.class);
		NetworkManager networkManager = mock(NetworkManager.class);
		LoginPresenter loginPresenter = new LoginPresenterImpl(loginView,loginRepository,networkManager);
		when(networkManager.isNetworkAvailable()).thenReturn(true);
		//Actually, you don't verify it in this situation, it only for demonstrates stub function.
//		assertTrue(networkManager.isNetworkAvailable());
		loginPresenter.login("hihi@abc.com", "hi");
		ArgumentCaptor<LoginRepository.Callback>callbackCaptor=ArgumentCaptor.forClass(LoginRepository.Callback.class);
		verify(loginRepository).login(eq("hihi@abc.com"), anyString(), callbackCaptor.capture());
		callbackCaptor.getValue().onSuccess();
		verify(loginView, times(1)).showLoading();
		verify(loginView, times(1)).hideLoading();
		verify(loginView).showSuccessMessage(anyString());

		// verifyNoMoreInteractions can be useful in some cases, but shouldn't be overused by using on all mocks in every test
//		verifyNoMoreInteractions(networkManager);
	}

	@Test
	public void login_failed_showFailedMessage() {
		when(networkManager.isNetworkAvailable()).thenReturn(true);
		loginPresenter.login("hihi@cba.com", "hi");
		verify(loginRepository).login(anyString(), anyString(), callbackCaptor.capture());
		callbackCaptor.getValue().onFail();

		//Mockito enables you to verify if interactions with a mock were performed in a given order using the InOrder API.
		// It is possible to create a group of mocks and verify the call order of all calls within that group.
		//
		InOrder inOrder = Mockito.inOrder(loginView);
		inOrder.verify(loginView, times(1)).showLoading();
		inOrder.verify(loginView, times(1)).hideLoading();
		inOrder.verify(loginView).showFailed(anyString());
	}

	@Test
	public void login_accountIsEmpty_showDataEmpty() {
		when(networkManager.isNetworkAvailable()).thenReturn(true);
		loginPresenter.login("", "123");
		verify(loginView, times(1)).showLoading();
		verify(loginRepository, never()).login(anyString(), anyString(), callbackCaptor.capture());
		verify(loginView, times(1)).hideLoading();
		verify(loginView).showInputDataIsEmpty();
	}

	@Test
	public void login_networkNotAvailable_showNetworkError() {
		when(networkManager.isNetworkAvailable()).thenReturn(false);
		loginPresenter.login("hihi@abc,com", "hi");
		verify(loginView, times(1)).showLoading();
		verify(loginRepository, never()).login(anyString(), anyString(), callbackCaptor.capture());
		verify(loginView, times(1)).hideLoading();
		verify(loginView).showNetworkError();
	}
}
