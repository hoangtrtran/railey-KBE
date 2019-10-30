package RaileyTest;

import htwb.ai.MyTest;

public class Test1 {

	@MyTest public boolean testmethod1returnsFalse() {
		boolean result = false;
		return result;
	}
	
	@MyTest public boolean testmethodWParameter(boolean param1) {
		boolean result = false;
		return result;
	}

	@MyTest private boolean testmethodprivate() {
		boolean result = false;
		return result;
	}

	@MyTest public int testmethodint() {
		int result = 1;
		return result;
	}
	
	@MyTest public void testmethodVoid() {
		int result = 1;
	}

	public boolean testmethodWOAnonotation() {
		boolean result = false;
		return result;
	}

	@MyTest public boolean testmethod2returnsTrue() {
		boolean result = true;
		return result;
	}
	
	@MyTest public boolean testmethodNPE() {
		Object reference = null;
		reference.toString();
		boolean result = false;
		return result;
	}
}
