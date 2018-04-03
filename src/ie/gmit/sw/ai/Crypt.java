package ie.gmit.sw.ai;

public interface Crypt {

	public String decrypt(String key);
	public String encrypt(String key, String text);
	
}
