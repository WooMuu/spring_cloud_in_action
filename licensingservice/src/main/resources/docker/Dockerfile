FROM java:8
RUN mkdir -p /usr/local/licensingservice
ADD licensing-service-0.0.1-SNAPSHOT.jar /usr/local/licensingservice/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh
