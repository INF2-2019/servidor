/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diario.disciplinas.views;

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
