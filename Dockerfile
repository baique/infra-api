FROM --platform=amd64 maven:3.8.5-openjdk-17 as builder
WORKDIR /app
COPY .build/settings.xml /root/.m2/settings.xml
COPY pom.xml .
RUN mvn clean package

FROM openjdk:17-buster
WORKDIR /app
ENV TZ=Asia/Shanghai

ENV spring.profiles.active=prd
ENV spring.devtools.restart.enabled=false
ENV spring.devtools.livereload.enabled=false
ENV XMX=512m
ENV XMS=512m

RUN echo "#!/bin/bash" >> /app/Start.sh ;\
echo "java " >> /app/Start.sh ;\
echo -n "-Xms$XMS" >> /app/Start.sh ;\
echo -n "-Xmx$XMX" >> /app/Start.sh ;\
echo -n "-XX:MaxDirectMemorySize=$XMS" >> /app/Start.sh ;\
echo -n "-XX:MetaspaceSize=256M" >> /app/Start.sh ;\
echo -n "-XX:MaxMetaspaceSize=256M" >> /app/Start.sh ;\
echo -n "-XX:+UseG1GC" >> /app/Start.sh ;\
echo -n "-XX:+UseStringDeduplication" >> /app/Start.sh ;\
echo -n "-XX:+ParallelRefProcEnabled" >> /app/Start.sh ;\
echo -n "-XX:+ExplicitGCInvokesConcurrent" >> /app/Start.sh ;\
echo -n "-XX:MaxGCPauseMillis=200" >> /app/Start.sh ;\
echo -n "-XX:ParallelGCThreads=4" >> /app/Start.sh ;\
echo -n "-XX:ConcGCThreads=2" >> /app/Start.sh ;\
echo -n "-Dfile.encoding=UTF-8" >> /app/Start.sh ;\
echo -n "-Duser.timezone=Asia/Shanghai" >> /app/Start.sh ;\
echo -n "-Djava.net.preferIPv4Stack=true" >> /app/Start.sh ;\
echo -n "-jar /app/app.jar" >> /app/Start.sh ;\
ln -sf /app/Start.sh /usr/bin/ZZ-FW-CMD ;\
chmod 0700 /app/Start.sh
CMD ["ZZ-FW-CMD"]
# 如有其他需要附带的文件，可在此处添加
COPY --from=builder /app/dist/lib/ ./
COPY --from=builder /app/dist/app.jar ./
EXPOSE 80
EXPOSE 443

















