keytool -import -trustcacerts -alias www.qtmap.com -keystore "%JAVA_HOME%/jre/lib/security/cacerts " -file "./www.qtmap.com.crt" -storepass changeit -noprompt

pause