import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public class StrUtils {
	//将json转化为ymal格式
    public static String json2Yml(String jsonStr) throws Exception {
        // parse JSON
        JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonStr);
        // save it as YAML
        String jsonAsYaml = new YAMLMapper().writeValueAsString(jsonNodeTree);
        return jsonAsYaml;
    }
}