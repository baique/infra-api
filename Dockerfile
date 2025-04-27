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
ENV server.port=80
ENV spring.profiles.active=prd


RUN echo "#!/bin/bash" >> /app/Start.sh ;\
echo "java -cp \"/app/*:/app/lib/*\" -Xms\$XMS -Xmx\$XMX -Dproject.name=\$PROJ_NAME  -Dfile.encoding=UTF-8 \$PROJ_PARAM tech.hljzj.framework.Bootstrap" >> /app/Start.sh ;\
ln -sf /app/Start.sh /usr/bin/ZZ-FW-CMD ;\
chmod 0700 /app/Start.sh
CMD ["ZZ-FW-CMD"]
# 如有其他需要附带的文件，可在此处添加
COPY --from=builder /app/ ./
EXPOSE 80
EXPOSE 443