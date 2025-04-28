FROM --platform=amd64 maven:3.8.5-openjdk-17 as builder
WORKDIR /app
COPY .build/settings.xml /root/.m2/settings.xml
COPY pom.xml .
RUN mvn dependency:copy-dependencies -DoutputDirectory=/app/lib
COPY src ./src
RUN mvn clean package && mv target/app.jar ./app.jar

FROM openjdk:17-buster
WORKDIR /app
ENV PROJ_NAME=app
ENV TZ=Asia/Shanghai
ENV XMS=256m
ENV XMX=256m

ENV spring.profiles.active=prd
ENV spring.devtools.restart.enabled=false
ENV spring.devtools.livereload.enabled=false

COPY /ca/ca.crt /tmp/ca.crt
COPY /ca/ssl.p12 /var/zz/ssl.p12
ENV server.ssl.key-store=/var/zz/ssl.p12
ENV server.ssl.key-store-password=123456
ENV server.ssl.key-store-type=PKCS12
ENV server.ssl.hsts.enabled=true
ENV server.ssl.hsts.max-age=31536000
ENV server.ssl.hsts.include-subdomains=true
ENV server.ssl.ciphers="TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384"
ENV server.ssl.protocol="TLSv1.2"
ENV server.ssl.enabled=true
ENV server.port=443

RUN keytool -importcert -trustcacerts -alias jxl-ca -file /tmp/ca.crt -storepass changeit -cacerts -noprompt && rm -f /tmp/ca.crt

RUN echo "#!/bin/bash" >> /app/Start.sh ;\
echo "java -cp \"/app/*:/app/lib/*\" -Xms\$XMS -Xmx\$XMX -Dproject.name=\$PROJ_NAME  -Dfile.encoding=UTF-8 \$PROJ_PARAM tech.hljzj.framework.Bootstrap" >> /app/Start.sh ;\
ln -sf /app/Start.sh /usr/bin/ZZ-FW-CMD ;\
chmod 0700 /app/Start.sh
CMD ["ZZ-FW-CMD"]
# 如有其他需要附带的文件，可在此处添加
COPY --from=builder /app/lib/ ./
COPY --from=builder /app/app.jar ./
EXPOSE 80
EXPOSE 443