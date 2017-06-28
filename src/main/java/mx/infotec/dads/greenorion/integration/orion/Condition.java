
package mx.infotec.dads.greenorion.integration.orion;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "attrs", "expression" })
public class Condition {

	@JsonProperty("attrs")
	private List<String> attrs = new ArrayList<>();
	@JsonProperty("expression")
	private Expression expression;

	@JsonProperty("attrs")
	public List<String> getAttrs() {
		return attrs;
	}

	@JsonProperty("attrs")
	public void setAttrs(List<String> attrs) {
		this.attrs = attrs;
	}

	@JsonProperty("expression")
	public Expression getExpression() {
		return expression;
	}

	@JsonProperty("expression")
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
}
