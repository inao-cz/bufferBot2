package me.inao.discordbot.connectors.http;

import me.inao.dbbp.annotations.Connector;
import me.inao.dbbp.connectors.http.Http;
import me.inao.dbbp.connectors.http.HttpConnector;

@Connector(name = HttpConnectorNames.TEST_CONNECTOR)
@Http(url = "")
public class TestConnector extends HttpConnector {

}
