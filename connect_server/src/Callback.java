import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
 
public class Callback {
	public static Object invoke(Object scope, String methodName,
	 Object... parameters) {
	 Object object = null;
	 
	Method method;
	 try {
		 method = scope.getClass().getMethod(methodName,
		 getParemeterClasses(parameters));
		 object = method.invoke(scope, parameters);
		 } 
	 catch (NoSuchMethodException | SecurityException
		 | InvocationTargetException | IllegalAccessException e) {
		 e.printStackTrace();
		}
	 
	 	return object;
		}	
	@SuppressWarnings("rawtypes")
	
	private static Class[] getParemeterClasses(Object... parameters) {
		 Class[] classes = new Class[parameters.length];
		 for (int i = 0; i < classes.length; i++) {
		 classes[i] = parameters[i].getClass();
		 }
		 return classes;
	 }
	
}