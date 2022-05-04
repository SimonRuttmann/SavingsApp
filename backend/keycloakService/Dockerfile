FROM quay.io/keycloak/keycloak:17.0.1 as builder

ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin

ENV KC_METRICS_ENABLED=true
ENV KC_FEATURES=token-exchange

#not Correct yet!!!!
#enable for external DB
ENV KC_DB=postgres

# change these values to point to a running postgres instance
ENV KC_DB_URL=jdbc:postgresql://localhost:5432/keycloak
#ENV KC_DB_USERNAME=postgres
#ENV KC_DB_PASSWORD=rootpassword
#ENV KC_HOSTNAME=localhost:5432

# test2
ENV DB_VENDOR: POSTGRES
ENV DB_ADDR: cAndD_postgresql
ENV DB_DATABASE: keycloak
ENV DB_USER: keycloak
ENV DB_SCHEMA: public
ENV DB_PASSWORD: rootpassword
ENV KEYCLOAK_USER: admin
ENV KEYCLOAK_PASSWORD: admin


#just Dev mode, Check later "start" version for produktion mode
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev"]

EXPOSE 8090:8080
