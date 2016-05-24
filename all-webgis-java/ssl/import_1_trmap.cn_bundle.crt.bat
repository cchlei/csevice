keytool -delete -alias trmap.cn -keystore "%JAVA_HOME%/jre/lib/security/cacerts " -file "./1_trmap.cn_bundle.crt" -storepass changeit
keytool -import -trustcacerts -alias trmap.cn -keystore "%JAVA_HOME%/jre/lib/security/cacerts " -file "E:\&tools&\eclipse\workspace\trmapv2\trmap\ssl\1_trmap.cn_bundle.crt" -storepass changeit -noprompt


pause