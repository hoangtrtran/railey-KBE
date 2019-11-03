package MyTestClasses;

import htwb.ai.MyTest;

public class TestClassFromRailey {

	@MyTest public boolean testMethod_Return_False() {
		return false;
	}
	
	@MyTest public boolean testMethod_With_Parameter(boolean param) {
		return false;
	}

	@MyTest private boolean testMethod_Private() {
		return false;
	}
	
	@MyTest protected boolean testMethod_Protected() {
		return false;
	}

	@MyTest public int testMethod_Int() {
		return 1;
	}
	
	@MyTest public String testMethod_String() {
		return "";
	}
	
	@MyTest public void testMethod_Void() {

	}

	public boolean testMethod_Without_Annotation() {
		return false;
	}
	
	@Override public String toString() {
		// testMethod with another annotation
		return "";
	}

	@MyTest public boolean testMethod_Return_True() {
		return true;
	}
	
	@SuppressWarnings("null")
	@MyTest public boolean testMethod_NullPointerException() {
		Object reference = null;
		reference.toString();
		return false;
	}
}
