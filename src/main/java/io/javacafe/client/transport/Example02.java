package io.javacafe.client.transport;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.xcontent.XContentFactory.jsonBuilder;

/**
 * 인덱스 생성
 *
 * @author User
 */
public class Example02 {

    @SuppressWarnings({"resource", "unchecked"})
    public static void main(String[] args) throws IOException {


        /**
         * 클라이언트 생성
         */
        TransportClient client = ClientTest.createClient();

        /**
         * 인덱스 생성
         */

        // Index명
        String INDEX_NAME = "moive_java";

        // 타입명
        String TYPE_NAME = "_doc";

        // 매핑정보
        XContentBuilder indexBuilder = jsonBuilder()
                .startObject()
					.startObject(TYPE_NAME)
						.startObject("properties")
							.startObject("movieCd")
								.field("type", "keyword")
								.field("store", "true")
								.field("index_options", "docs")
							.endObject()
							.startObject("movieNm")
								.field("type", "text")
								.field("store", "true")
								.field("index_options", "docs")
							.endObject()
                			.startObject("movieNmEn")
								.field("type", "text")
								.field("store", "true")
								.field("index_options", "docs")
                			.endObject()
                		.endObject()
                	.endObject()
                .endObject();

        // 인덱스 생성
        client.admin().indices().prepareCreate(INDEX_NAME)
                .setSettings(Settings.builder()
                        .put("index.number_of_shards", 3)
                        .put("index.number_of_replicas", 1)
                )
                .addMapping(TYPE_NAME, indexBuilder)
                .get();


        /**
         * 클라이언트 종료
         */
        client.close();


    }
}
