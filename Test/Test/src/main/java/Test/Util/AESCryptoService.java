package Test.Util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class AESCryptoService implements CryptoService {

	private static final String ALGORITHM = "AES/ECB/PKCS5Padding"; // 권장 알고리즘
	private static final SecretKey SECRET_KEY = new SecretKeySpec("my-fixed-key-123".trim().getBytes(), "AES"); // 공백 제거);

//	static {
//		try {
//			KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
//			keyGen.init(128);
//			SECRET_KEY = keyGen.generateKey();
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to initialize secret key ", e);
//		}
//
//	}

	@Override
	public String encrypt(String plainText) {
	    try {
	        // 입력값 검증
	        if (plainText == null || plainText.isEmpty()) {
	            throw new IllegalArgumentException("Input text cannot be null or empty");
	        }

	        // Cipher 인스턴스 생성
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        System.out.println("Cipher initialized successfully");

	        // 암호화 모드 설정
	        cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);
	        System.out.println("Cipher set to ENCRYPT_MODE");

	        // 암호화 실행
	        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
	        System.out.println("Encryption successful");

	        // Base64 인코딩 후 반환
	        return Base64.getEncoder().encodeToString(encryptedBytes);

	    } catch (Exception e) {
	        System.err.println("Encryption failed: " + e.getMessage());
	        e.printStackTrace();
	        throw new RuntimeException("Encryption failed", e);
	    }
	}

	@Override
	public String decrypt(String encryptedText) {

		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);// Cipher 인스턴스 생성 (AES)
			cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY); // 복호화 모드 초기화
			byte[] decoded = Base64.getDecoder().decode(encryptedText);// Base64 디코딩
			return new String(cipher.doFinal(decoded));// 복호화 실행 후 문자열 반환
		} catch (Exception e) {
			throw new RuntimeException("Decrytion Failed", e);// 실패 시 예외 처리
		}

	}
	
//	1. Cipher는 암복호화의 주체
//	Cipher는 암호화와 복호화를 수행하는 주체로, 키와 알고리즘을 이용해 데이터를 처리합니다.
//	Cipher.init(mode, key)를 통해 **키와 작업 모드(암호화/복호화)**를 설정합니다.
//	2. Cipher는 키를 "가지고 있는 상태"
//	Cipher.init()을 호출하면 키와 모드가 설정되며, 이 상태를 유지해 암복호화를 수행합니다.
//	동일한 키를 사용하더라도, 작업을 수행하기 전에 Cipher 객체를 새로 생성하고 초기화해야 합니다.
//	3. 충돌 방지를 위해 새 객체 생성 및 초기화
//	같은 Cipher 객체를 재사용하면 내부 상태가 꼬여 암복호화가 실패하거나 잘못된 결과를 초래할 수 있습니다.
//	각 암호화/복호화 작업은 새로운 Cipher 객체를 생성하고 초기화하여 독립적으로 수행해야 합니다.
//	4. 키가 같아도 결과가 달라질 수 있는 이유
//	초기화 벡터(IV)와 암호화 모드(CBC 등)가 결과에 영향을 미칩니다.
//	이를 통해 동일한 데이터와 키라도, 매번 다른 암호화 결과를 생성하여 보안을 강화합니다.
	
//	암복호화 진행 중 발생한 문제와 해결 과정
//	1. 발생했던 주요 문제
//	동적 키 문제
//
//	KeyGenerator를 사용하여 키를 동적으로 생성함으로써, 가입 시와 로그인 시 서로 다른 키로 암호화/복호화가 이루어짐.
//	결과적으로 암호화된 비밀번호가 일치하지 않아 로그인 실패.
//	키 길이 문제
//
//	AES는 16, 24, 32 바이트 고정 키를 요구하지만, 사용된 키 문자열에 보이지 않는 공백이나 줄바꿈 문자가 포함되어 17바이트로 처리됨.
//	이로 인해 Invalid AES key length 오류 발생.
//	암호화 실패
//
//	입력 데이터가 null이거나 빈 문자열일 경우, 암호화 과정에서 예외 발생.
//	알고리즘 설정이 명시적이지 않아 일부 블록에서 오류가 발생.
//	2. 해결 과정
//	고정된 키 사용
//
//	동적으로 생성되던 키를 제거하고, 고정된 키로 암호화/복호화가 동일한 환경에서 이루어지도록 설정.
//	키 길이 문제 해결
//
//	고정 키를 정확히 16바이트로 설정하여 AES 알고리즘 요구사항 충족.
//	불필요한 공백이나 줄바꿈 문자를 제거하여 키 길이 문제 방지.
//	암호화/복호화 로직 점검 및 개선
//
//	알고리즘을 명시적으로 설정하여 안정성 확보.
//	입력값 유효성 검증을 추가해 null이나 빈 문자열 처리.
//	디버깅과 로그 활용
//
//	암호화 과정에서 발생한 예외를 로그로 출력하여 문제 원인을 분석.
//	로그를 통해 키와 입력값의 상태를 확인하고 로직 수정.
//	3. 최종 결과
//	동적 키 문제 해결
//
//	고정된 키 사용으로 암호화와 복호화 값이 일치.
//	가입 시 저장된 암호화된 비밀번호와 로그인 시 비교가 정상적으로 동작.
//	가입 및 로그인 정상 동작
//
//	데이터베이스에 암호화된 비밀번호가 저장되고, 로그인 시 입력값과의 비교가 성공적으로 이루어짐.
//	학습 목표 달성
//
//	암호화/복호화의 원리를 이해하고, 실제 상황에서 발생할 수 있는 문제를 해결하는 방법을 학습.
	

}
