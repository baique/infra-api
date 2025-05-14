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

RUN echo "#!/bin/bash" >> /app/Start.sh ;\
echo "java " >> /app/Start.sh ;\
echo "-Xms$XMS" >> /app/Start.sh ;\
echo "-Xmx$XMX" >> /app/Start.sh ;\
echo "-XX:MaxDirectMemorySize=$XMS" >> /app/Start.sh ;\
echo "-XX:MetaspaceSize=256M" >> /app/Start.sh ;\
echo "-XX:MaxMetaspaceSize=256M" >> /app/Start.sh ;\
echo "-XX:+UseG1GC" >> /app/Start.sh ;\
echo "-XX:+AlwaysPreTouch" >> /app/Start.sh ;\
echo "-XX:-ResizePLAB" >> /app/Start.sh ;\
echo "-XX:+ParallelRefProcEnabled" >> /app/Start.sh ;\
echo "-XX:+ExplicitGCInvokesConcurrent" >> /app/Start.sh ;\
echo "-XX:MaxGCPauseMillis=50" >> /app/Start.sh ;\
echo "-XX:+UseStringDeduplication" >> /app/Start.sh ;\
echo "-XX:+UnlockExperimentalVMOptions" >> /app/Start.sh ;\
echo "-XX:G1NewSizePercent=10" >> /app/Start.sh ;\
echo "-XX:InitiatingHeapOccupancyPercent=45" >> /app/Start.sh ;\
echo "-Duser.timezone=Asia/Shanghai" >> /app/Start.sh ;\
echo "-Dfile.encoding=UTF-8" >> /app/Start.sh ;\
echo "-jar /app/app.jar" >> /app/Start.sh ;\
ln -sf /app/Start.sh /usr/bin/ZZ-FW-CMD ;\
chmod 0700 /app/Start.sh
CMD ["ZZ-FW-CMD"]
# 如有其他需要附带的文件，可在此处添加
COPY --from=builder /app/dist/lib/ ./
COPY --from=builder /app/dist/app.jar ./
EXPOSE 80
EXPOSE 443

















