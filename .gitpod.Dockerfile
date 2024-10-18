FROM mcr.microsoft.com/playwright/java:v1.47.0

RUN apt update \
    && apt install -yq npm \
    && apt install -yq sudo

RUN apt update \
    && apt install -yq openjdk-17-jdk \
    && apt install -yq openjdk-17-jre \
    && apt install -yq git
