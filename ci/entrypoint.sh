#!/usr/bin/env bash
set -Eeo pipefail

# usage: file_env VAR [DEFAULT]
#    ie: file_env 'XYZ_DB_PASSWORD' 'example'
# (will allow for "$XYZ_DB_PASSWORD_FILE" to fill in the value of
#  "$XYZ_DB_PASSWORD" from a file, especially for Docker's secrets feature)

file_env() {
	local var="$1"
	local fileVar="${var}_FILE"
	local def="${2:-}"
	if [ "${!var:-}" ] && [ "${!fileVar:-}" ]; then
		echo >&2 "error: both $var and $fileVar are set (but are exclusive)"
		exit 1
	fi
	local val="$def"
	if [ "${!var:-}" ]; then
		val="${!var}"
	elif [ "${!fileVar:-}" ]; then
		val="$(< "${!fileVar}")"
	fi
	export "$var"="$val"
	unset "$fileVar"
}

file_env 'DBUSERNAME'
file_env 'DBPASSWORD'

echo "spring envs..."
echo "spring profile = $PROFILE"

echo "db envs..."
echo "db host = $DBHOST"
echo "db port = $DBPORT"
echo "db name = $DBNAME"

echo "jennifer envs..."
echo "JNF_SERVER_ADDRESS = $JNF_SERVER_ADDRESS"
echo "JNF_SERVER_PORT = $JNF_SERVER_PORT"
echo "JNF_DOMAIN_ID = $JNF_DOMAIN_ID"
echo "JNF_INSTANCE_ID = $JNF_INSTANCE_ID"
echo "JNF_INSTANCE_NAME = $JNF_INSTANCE_NAME"

# jennifer config file settings
sed -i 's/server_address_input/'''$JNF_SERVER_ADDRESS'''/' $AGENT_CONF_FILE
sed -i 's/server_port_input/'''$JNF_SERVER_PORT'''/' $AGENT_CONF_FILE
sed -i 's/domain_id_input/'''$JNF_DOMAIN_ID'''/' $AGENT_CONF_FILE
sed -i 's/inst_id_input/'''$JNF_INSTANCE_ID'''/' $AGENT_CONF_FILE
sed -i 's/inst_name_input/'''$JNF_INSTANCE_NAME'''/' $AGENT_CONF_FILE

# java run
java -jar \
    -Dspring.profiles.active="$PROFILE" \
	-Dspring.datasource.url="jdbc:sqlserver://$DBHOST:$DBPORT;DatabaseName=$DBNAME;" \
	-Dspring.datasource.username="$DBUSERNAME" \
    -Dspring.datasource.password="$DBPASSWORD" \
	-javaagent:$AGENT_HOME/jennifer.jar \
	-Djennifer.config=$AGENT_HOME/conf/jennifer.conf \
    /app.jar
