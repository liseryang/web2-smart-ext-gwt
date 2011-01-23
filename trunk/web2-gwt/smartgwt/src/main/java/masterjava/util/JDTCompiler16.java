package masterjava.util;

import org.apache.tools.ant.taskdefs.Javac;
import org.eclipse.jdt.core.JDTCompilerAdapter;

/**
 * User: GKislin
 * Date: 20.12.2010
 *
 * Hosted mode JSP compilation java 1.5 compatibility workaround
 * http://code.google.com/p/google-web-toolkit/issues/detail?id=3557
 *
 * Set GWT run java option -Dbuild.compiler=masterjava.util.JDTCompiler16
 */
public class JDTCompiler16 extends JDTCompilerAdapter {
    @Override
    public void setJavac(Javac attributes) {
        if (attributes.getTarget() == null) {
            attributes.setTarget("1.6");
        }
        if (attributes.getSource() == null) {
            attributes.setSource("1.6");
        }
        super.setJavac(attributes);
    }
}
