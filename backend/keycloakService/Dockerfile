FROM quay.io/keycloak/keycloak:17.0.1 as builder

ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin

ENV KC_METRICS_ENABLED=true
ENV KC_FEATURES=token-exchange

#enable for external DB
#ENV KC_DB=postgres

# change these values to point to a running postgres instance
#ENV KC_DB_URL=<DBURL>
#ENV KC_DB_USERNAME=<DBUSERNAME>
#ENV KC_DB_PASSWORD=<DBPASSWORD>
#ENV KC_HOSTNAME=localhost:8443

EXPOSE 8090:8080

#just Dev mode, Check later "start" version for produktion mode
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev"]