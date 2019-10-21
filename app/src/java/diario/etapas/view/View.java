package diario.etapas.view;

import diario.etapas.RenderException;
import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class View <D> {
	protected D data;

	public View(D d) {
		this.data = d;
	}

	public abstract void render(PrintWriter writer) throws RenderException;

	public String render() throws RenderException{
		StringWriter data = new StringWriter();
		PrintWriter printWriter = new PrintWriter(data);
		render(printWriter);
		return data.toString();
	};
}
