/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2006, by Barak Naveh and Contributors.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/* ------------------
 * DOTExporter.java
 * ------------------
 * (C) Copyright 2006, by Trevor Harmon.
 *
 * Original Author:  Trevor Harmon <trevor@vocaro.com>
 *
 */
package org.jgrapht.ext;

import java.io.*;
import java.util.*;

import org.jgrapht.*;
import org.jgrapht.util.*;


/** Exports a graph into a DOT file.
 *
 * <p>For a description of the format see 
 * <a href="http://en.wikipedia.org/wiki/DOT_language">http://en.wikipedia.org/wiki/DOT_language</a>.</p>
 * 
 * @author Trevor Harmon
 */
public class DOTExporter<V, E>
{
    private VertexNameProvider<V> vertexIDProvider;
    private VertexNameProvider<V> vertexLabelProvider;
    private EdgeNameProvider<E> edgeLabelProvider;
    
    /** Constructs a new DOTExporter object with an integer name provider for
     *  the vertex IDs and null providers for the vertex and edge labels.
     */
    public DOTExporter()
    {
        this(new IntegerNameProvider<V>(), null, null);
    }
    
    /** Constructs a new DOTExporter object with the given ID and label providers.
     *
     *  @param vertexIDProvider for generating vertex IDs. Must not be null.
     *  @param vertexLabelProvider for generating vertex labels. If null, vertex labels will not be written to the file.
     *  @param edgeLabelProvider for generating edge labels. If null, edge labels will not be written to the file.
     */
    public DOTExporter(VertexNameProvider<V> vertexIDProvider, VertexNameProvider<V> vertexLabelProvider, EdgeNameProvider<E> edgeLabelProvider)
    {
        this.vertexIDProvider = vertexIDProvider;
        this.vertexLabelProvider = vertexLabelProvider;
        this.edgeLabelProvider = edgeLabelProvider;
    }
    
    /** Exports a graph into a plain text file in DOT format. 
     *
     *  @param output the writer to which the graph to be exported
     *  @param g the graph to be exported
     */
    public void export(Writer writer, Graph<V,E> g)
    {
        PrintWriter out = new PrintWriter(writer);
        String indent = "  ";
        String connector;
        
        if (g instanceof DirectedGraph)
        {
    		out.println("digraph G {");
    		connector = " -> ";
        }
        else
        {
    		out.println("graph G {");
    		connector = " -- ";
    	}

		for (V v : g.vertexSet())
		{
		    out.print(indent + vertexIDProvider.getVertexName(v));
		    
		    if (vertexLabelProvider != null)
		        out.print(" [label = \"" + vertexLabelProvider.getVertexName(v) + "\"]");
		    
            out.println(";");
		}

        for (E e : g.edgeSet())
		{
		    String source = vertexIDProvider.getVertexName(g.getEdgeSource(e));
		    String target = vertexIDProvider.getVertexName(g.getEdgeTarget(e));

			out.print(indent + source + connector + target);
			
			if (edgeLabelProvider != null)
                out.print(" [label = \"" + edgeLabelProvider.getEdgeName(e) + "\"]");
                
            out.println(";");
		}

		out.println("}");
		
		out.flush();
    }
}
