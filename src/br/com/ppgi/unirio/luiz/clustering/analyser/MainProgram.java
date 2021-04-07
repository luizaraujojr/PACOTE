package br.com.ppgi.unirio.luiz.clustering.analyser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import br.com.ppgi.unirio.luiz.clustering.calculator.MQCalculator;
import br.com.ppgi.unirio.luiz.clustering.model.Dependency;
import br.com.ppgi.unirio.luiz.clustering.model.Project;
import br.com.ppgi.unirio.luiz.clustering.model.ProjectClass;
import br.com.ppgi.unirio.luiz.clustering.model.ProjectPackage;
import br.com.ppgi.unirio.luiz.clustering.mojo.MoJo;
import br.com.ppgi.unirio.marlon.smc.experiment.algorithm.LNSParameterTest;
import br.com.ppgi.unirio.marlon.smc.instance.file.InstanceParseException;

@SuppressWarnings("unused")
public class MainProgram
{
	
	private static List<String> instanceFilenames = new ArrayList<String>();
			
	
	public static Vector<Project> readJarInstances(List<String> instanceFilenamesArray) throws Exception
	{
		Vector<Project> instances = new Vector<Project>();
		
		CDAJarReader reader = new CDAJarReader();

		reader.framework.add("apple.laf.AquaFileChooserUI");
		reader.framework.add("bsh.classpath.ClassManagerImpl");
		reader.framework.add("CH.ifa.draw.standard.AlignCommand$1");
		reader.framework.add("ch.randelshofer.quaqua.QuaquaManager");
		reader.framework.add("com.apple.eawt.Application");
		reader.framework.add("com.apple.eawt.ApplicationAdapter");
		reader.framework.add("com.apple.eawt.ApplicationEvent");
		reader.framework.add("com.apple.eawt.ApplicationListener");
		reader.framework.add("com.apple.eio.FileManager");
		reader.framework.add("com.apple.mrj.jdirect.Linker");
		reader.framework.add("com.apple.mrj.jdirect.MethodClosure");
		reader.framework.add("com.apple.mrj.jdirect.MethodClosureUPP");
		reader.framework.add("com.apple.mrj.macos.libraries.InterfaceLib");
		reader.framework.add("com.apple.mrj.MRJAboutHandler");
		reader.framework.add("com.apple.mrj.MRJApplicationUtils");
		reader.framework.add("com.apple.mrj.MRJFileUtils");
		reader.framework.add("com.apple.mrj.MRJOpenApplicationHandler");
		reader.framework.add("com.apple.mrj.MRJOpenDocumentHandler");
		reader.framework.add("com.apple.mrj.MRJOSType");
		reader.framework.add("com.apple.mrj.MRJPrefsHandler");
		reader.framework.add("com.apple.mrj.MRJPrintDocumentHandler");
		reader.framework.add("com.apple.mrj.MRJQuitHandler");
		reader.framework.add("com.apple.mrj.swing.MacFileChooserUI");
		reader.framework.add("com.ibm.uvm.tools.DebugSupport");
		reader.framework.add("com.sun.awt.AWTUtilities");
		reader.framework.add("com.sun.image.codec.jpeg.JPEGCodec");
		reader.framework.add("com.sun.image.codec.jpeg.JPEGDecodeParam");
		reader.framework.add("com.sun.image.codec.jpeg.JPEGImageDecoder");
		reader.framework.add("com.sun.tools.javac.Main");
		reader.framework.add("edu.stanford.ejalbert.BrowserLauncher");
		reader.framework.add("edu.umd.cs.findbugs.annotations.Nullable");
		reader.framework.add("FirewallPlugin");
		reader.framework.add("glguerin.io.FileForker");
		reader.framework.add("glguerin.io.Pathname");
		reader.framework.add("glguerin.util.MacPlatform");
		reader.framework.add("java.awt.BorderLayout");
		reader.framework.add("java.awt.Button");
		reader.framework.add("java.awt.Canvas");
		reader.framework.add("java.awt.Checkbox");
		reader.framework.add("java.awt.Color");
		reader.framework.add("java.awt.Component");
		reader.framework.add("java.awt.Container");
		reader.framework.add("java.awt.Cursor");
		reader.framework.add("java.awt.Dialog");
		reader.framework.add("java.awt.Dimension");
		reader.framework.add("java.awt.event.ActionEvent");
		reader.framework.add("java.awt.event.ActionListener");
		reader.framework.add("java.awt.event.ItemEvent");
		reader.framework.add("java.awt.event.ItemListener");
		reader.framework.add("java.awt.event.KeyAdapter");
		reader.framework.add("java.awt.event.KeyEvent");
		reader.framework.add("java.awt.event.KeyListener");
		reader.framework.add("java.awt.event.MouseAdapter");
		reader.framework.add("java.awt.event.MouseEvent");
		reader.framework.add("java.awt.event.MouseListener");
		reader.framework.add("java.awt.event.TextEvent");
		reader.framework.add("java.awt.event.TextListener");
		reader.framework.add("java.awt.event.WindowAdapter");
		reader.framework.add("java.awt.event.WindowEvent");
		reader.framework.add("java.awt.event.WindowListener");
		reader.framework.add("java.awt.FlowLayout");
		reader.framework.add("java.awt.Font");
		reader.framework.add("java.awt.Frame");
		reader.framework.add("java.awt.Graphics");
		reader.framework.add("java.awt.GridBagConstraints");
		reader.framework.add("java.awt.GridBagLayout");
		reader.framework.add("java.awt.GridLayout");
		reader.framework.add("java.awt.Image");
		reader.framework.add("java.awt.image.ImageObserver");
		reader.framework.add("java.awt.image.ImageProducer");
		reader.framework.add("java.awt.Insets");
		reader.framework.add("java.awt.Label");
		reader.framework.add("java.awt.LayoutManager");
		reader.framework.add("java.awt.List");
		reader.framework.add("java.awt.MediaTracker");
		reader.framework.add("java.awt.Menu");
		reader.framework.add("java.awt.MenuBar");
		reader.framework.add("java.awt.MenuItem");
		reader.framework.add("java.awt.Panel");
		reader.framework.add("java.awt.Rectangle");
		reader.framework.add("java.awt.SystemColor");
		reader.framework.add("java.awt.TextArea");
		reader.framework.add("java.awt.TextComponent");
		reader.framework.add("java.awt.TextField");
		reader.framework.add("java.awt.Toolkit");
		reader.framework.add("java.awt.Window");
		reader.framework.add("java.beans.BeanInfo");
		reader.framework.add("java.beans.IntrospectionException");
		reader.framework.add("java.beans.Introspector");
		reader.framework.add("java.beans.PropertyChangeEvent");
		reader.framework.add("java.beans.PropertyChangeListener");
		reader.framework.add("java.beans.PropertyChangeSupport");
		reader.framework.add("java.beans.PropertyDescriptor");
		reader.framework.add("java.beans.PropertyVetoException");
		reader.framework.add("java.io.BufferedReader");
		reader.framework.add("java.io.BufferedWriter");
		reader.framework.add("java.io.ByteArrayOutputStream");
		reader.framework.add("java.io.File");
		reader.framework.add("java.io.DataInputStream");
		reader.framework.add("java.io.DataOutputStream");
		reader.framework.add("java.io.InputStreamReader");
		reader.framework.add("java.lang.InternalError");
		reader.framework.add("javax.swing.JEditorPane");
		reader.framework.add("javax.swing.JFileChooser");
		reader.framework.add("javax.swing.Box");
		reader.framework.add("javax.swing.BoxLayout");
		reader.framework.add("javax.swing.border.EmptyBorder");
		reader.framework.add("javax.swing.ButtonModel");
		reader.framework.add("java.lang.Runtime");
		reader.framework.add("java.lang.Process");
		reader.framework.add("java.io.BufferedOutputStream");
		reader.framework.add("java.io.FileInputStream");
		reader.framework.add("java.io.FileNotFoundException");
		reader.framework.add("java.io.FileOutputStream");
		reader.framework.add("java.io.FileReader");
		reader.framework.add("java.io.FileWriter");
		reader.framework.add("java.io.InputStream");
		reader.framework.add("java.io.IOException");
		reader.framework.add("java.io.ObjectInputStream");
		reader.framework.add("java.io.ObjectInputStream$GetField");
		reader.framework.add("java.io.ObjectOutputStream");
		reader.framework.add("java.io.ObjectOutputStream$PutField");
		reader.framework.add("java.io.ObjectStreamClass");
		reader.framework.add("java.io.ObjectStreamField");
		reader.framework.add("java.io.OutputStream");
		reader.framework.add("java.io.PrintStream");
		reader.framework.add("java.io.PrintWriter");
		reader.framework.add("java.io.Reader");
		reader.framework.add("java.io.Serializable");
		reader.framework.add("java.io.StringReader");
		reader.framework.add("java.io.StringWriter");
		reader.framework.add("java.io.Writer");
		reader.framework.add("java.lang.annotation.Annotation");
		reader.framework.add("java.lang.annotation.Documented");
		reader.framework.add("java.lang.annotation.ElementType");
		reader.framework.add("java.lang.annotation.Inherited");
		reader.framework.add("java.lang.annotation.Retention");
		reader.framework.add("java.lang.annotation.RetentionPolicy");
		reader.framework.add("java.lang.annotation.Target");
		reader.framework.add("java.lang.Appendable");
		reader.framework.add("java.lang.AssertionError");
		reader.framework.add("java.lang.Boolean");
		reader.framework.add("java.lang.Byte");
		reader.framework.add("java.lang.Character");
		reader.framework.add("java.lang.CharSequence");
		reader.framework.add("java.lang.Class");
		reader.framework.add("java.lang.ClassCastException");
		reader.framework.add("java.lang.ClassLoader");
		reader.framework.add("java.lang.ClassNotFoundException");
		reader.framework.add("java.lang.Deprecated");
		reader.framework.add("java.lang.Double");
		reader.framework.add("java.lang.Enum");
		reader.framework.add("java.lang.Error");
		reader.framework.add("java.lang.Exception");
		reader.framework.add("java.lang.Float");
		reader.framework.add("java.lang.IllegalAccessException");
		reader.framework.add("java.lang.IllegalArgumentException");
		reader.framework.add("java.lang.IllegalStateException");
		reader.framework.add("java.lang.IllegalThreadStateException");
		reader.framework.add("java.lang.InstantiationException");
		reader.framework.add("java.lang.Integer");
		reader.framework.add("java.lang.InterruptedException");
		reader.framework.add("java.lang.Iterable");
		reader.framework.add("java.lang.Long");
		reader.framework.add("java.lang.management.ManagementFactory");
		reader.framework.add("java.lang.management.RuntimeMXBean");
		reader.framework.add("java.lang.management.ThreadMXBean");
		reader.framework.add("java.lang.Math");
		reader.framework.add("java.lang.NoClassDefFoundError");
		reader.framework.add("java.lang.NoSuchFieldError");
		reader.framework.add("java.lang.NoSuchMethodError");
		reader.framework.add("java.lang.NoSuchMethodException");
		reader.framework.add("java.lang.NullPointerException");
		reader.framework.add("java.lang.Number");
		reader.framework.add("java.lang.NumberFormatException");
		reader.framework.add("java.lang.Object");
		reader.framework.add("java.lang.reflect.Array");
		reader.framework.add("java.lang.reflect.Constructor");
		reader.framework.add("java.lang.reflect.Field");
		reader.framework.add("java.lang.reflect.GenericArrayType");
		reader.framework.add("java.lang.reflect.InvocationTargetException");
		reader.framework.add("java.lang.reflect.Method");
		reader.framework.add("java.lang.reflect.Modifier");
		reader.framework.add("java.lang.reflect.ParameterizedType");
		reader.framework.add("java.lang.reflect.Type");
		reader.framework.add("java.lang.reflect.TypeVariable");
		reader.framework.add("java.lang.reflect.WildcardType");
		reader.framework.add("java.lang.Runnable");
		reader.framework.add("java.lang.RuntimeException");
		reader.framework.add("java.lang.SecurityException");
		reader.framework.add("java.lang.Short");
		reader.framework.add("java.lang.StackOverflowError");
		reader.framework.add("java.lang.StackTraceElement");
		reader.framework.add("java.lang.String");
		reader.framework.add("java.lang.StringBuffer");
		reader.framework.add("java.lang.StringBuilder");
		reader.framework.add("java.lang.System");
		reader.framework.add("java.lang.Thread");
		reader.framework.add("java.lang.Thread$State");
		reader.framework.add("java.lang.ThreadDeath");
		reader.framework.add("java.lang.ThreadGroup");
		reader.framework.add("java.lang.ThreadLocal");
		reader.framework.add("java.lang.Throwable");
		reader.framework.add("java.lang.UnsupportedOperationException");
		reader.framework.add("java.lang.Void");
		reader.framework.add("java.math.BigInteger");
		reader.framework.add("java.net.Authenticator");
		reader.framework.add("java.net.HttpURLConnection");
		reader.framework.add("java.net.InetAddress");
		reader.framework.add("java.net.MalformedURLException");
		reader.framework.add("java.net.PasswordAuthentication");
		reader.framework.add("java.net.ServerSocket");
		reader.framework.add("java.net.Socket");
		reader.framework.add("java.net.UnknownHostException");
		reader.framework.add("java.net.URI");
		reader.framework.add("java.net.URISyntaxException");
		reader.framework.add("java.net.URL");
		reader.framework.add("java.net.URLClassLoader");
		reader.framework.add("java.net.URLConnection");
		reader.framework.add("java.net.URLDecoder");
		reader.framework.add("java.net.URLEncoder");
		reader.framework.add("java.net.URLStreamHandler");
		reader.framework.add("java.nio.Buffer");
		reader.framework.add("java.nio.ByteBuffer");
		reader.framework.add("java.nio.channels.FileChannel");
		reader.framework.add("java.nio.CharBuffer");
		reader.framework.add("java.nio.charset.CharacterCodingException");
		reader.framework.add("java.nio.charset.Charset");
		reader.framework.add("java.nio.charset.CharsetDecoder");
		reader.framework.add("java.nio.charset.CharsetEncoder");
		reader.framework.add("java.nio.charset.CodingErrorAction");
		reader.framework.add("java.nio.charset.IllegalCharsetNameException");
		reader.framework.add("java.nio.charset.MalformedInputException");
		reader.framework.add("java.nio.charset.UnsupportedCharsetException");
		reader.framework.add("java.nio.file.CopyOption");
		reader.framework.add("java.nio.file.Files");
		reader.framework.add("java.nio.file.FileSystem");
		reader.framework.add("java.nio.file.FileSystems");
		reader.framework.add("java.nio.file.Path");
		reader.framework.add("java.nio.file.StandardCopyOption");
		reader.framework.add("java.security.AccessControlException");
		reader.framework.add("java.security.AccessController");
		reader.framework.add("java.security.MessageDigest");
		reader.framework.add("java.security.NoSuchAlgorithmException");
		reader.framework.add("java.security.Permission");
		reader.framework.add("java.security.PrivilegedAction");
		reader.framework.add("java.text.AttributedCharacterIterator");
		reader.framework.add("java.text.AttributedCharacterIterator$Attribute");
		reader.framework.add("java.text.AttributedString");
		reader.framework.add("java.text.BreakIterator");
		reader.framework.add("java.text.CharacterIterator");
		reader.framework.add("java.text.CollationKey");
		reader.framework.add("java.text.Collator");
		reader.framework.add("java.text.DateFormat");
		reader.framework.add("java.text.DateFormat$Field");
		reader.framework.add("java.text.DecimalFormat");
		reader.framework.add("java.text.FieldPosition");
		reader.framework.add("java.text.Format");
		reader.framework.add("java.text.Format$Field");
		reader.framework.add("java.text.MessageFormat");
		reader.framework.add("java.text.NumberFormat");
		reader.framework.add("java.text.ParseException");
		reader.framework.add("java.text.RuleBasedCollator");
		reader.framework.add("java.text.SimpleDateFormat");
		reader.framework.add("java.util.AbstractList");
		reader.framework.add("java.util.ArrayList");
		reader.framework.add("java.util.Arrays");
		reader.framework.add("java.util.Collection");
		reader.framework.add("java.util.Collections");
		reader.framework.add("java.util.Comparator");
		reader.framework.add("java.util.concurrent.atomic.AtomicInteger");
		reader.framework.add("java.util.concurrent.atomic.AtomicLong");
		reader.framework.add("java.util.concurrent.Callable");
		reader.framework.add("java.util.concurrent.ConcurrentHashMap");
		reader.framework.add("java.util.concurrent.ConcurrentLinkedQueue");
		reader.framework.add("java.util.concurrent.ConcurrentMap");
		reader.framework.add("java.util.concurrent.CopyOnWriteArrayList");
		reader.framework.add("java.util.concurrent.CountDownLatch");
		reader.framework.add("java.util.concurrent.ExecutionException");
		reader.framework.add("java.util.concurrent.Executors");
		reader.framework.add("java.util.concurrent.ExecutorService");
		reader.framework.add("java.util.concurrent.Future");
		reader.framework.add("java.util.concurrent.FutureTask");
		reader.framework.add("java.util.concurrent.locks.Lock");
		reader.framework.add("java.util.concurrent.locks.ReentrantLock");
		reader.framework.add("java.util.concurrent.TimeoutException");
		reader.framework.add("java.util.concurrent.TimeUnit");
		reader.framework.add("java.util.Enumeration");
		reader.framework.add("java.util.HashMap");
		reader.framework.add("java.util.HashSet");
		reader.framework.add("java.util.Hashtable");
		reader.framework.add("java.util.IdentityHashMap");
		reader.framework.add("java.util.Iterator");
		reader.framework.add("java.util.LinkedHashMap");
		reader.framework.add("java.util.LinkedHashSet");
		reader.framework.add("java.util.List");
		reader.framework.add("java.util.Map");
		reader.framework.add("java.util.Map$Entry");
		reader.framework.add("java.util.Properties");
		reader.framework.add("java.util.Random");
		reader.framework.add("java.util.regex.Matcher");
		reader.framework.add("java.util.regex.Pattern");
		reader.framework.add("java.util.Set");
		reader.framework.add("java.util.StringTokenizer");
		reader.framework.add("java.util.Vector");
		reader.framework.add("java.util.zip.ZipEntry");
		reader.framework.add("java.util.zip.ZipFile");
		reader.framework.add("javax.accessibility.Accessible");
		reader.framework.add("javax.accessibility.AccessibleContext");
		reader.framework.add("javax.annotation.concurrent.GuardedBy");
		reader.framework.add("javax.annotation.concurrent.ThreadSafe");
		reader.framework.add("javax.annotation.Nonnull");
		reader.framework.add("javax.annotation.Nullable");
		reader.framework.add("javax.imageio.ImageIO");
		reader.framework.add("javax.jnlp.ServiceManager");
		reader.framework.add("javax.print.attribute.Attribute");
		reader.framework.add("javax.print.attribute.AttributeSet");
		reader.framework.add("javax.print.attribute.DocAttribute");
		reader.framework.add("javax.print.attribute.DocAttributeSet");
		reader.framework.add("javax.print.attribute.HashAttributeSet");
		reader.framework.add("javax.print.attribute.HashDocAttributeSet");
		reader.framework.add("javax.print.attribute.HashPrintRequestAttributeSet");
		reader.framework.add("javax.print.attribute.IntegerSyntax");
		reader.framework.add("javax.print.attribute.PrintJobAttribute");
		reader.framework.add("javax.print.attribute.PrintRequestAttribute");
		reader.framework.add("javax.print.attribute.PrintRequestAttributeSet");
		reader.framework.add("javax.print.attribute.Size2DSyntax");
		reader.framework.add("javax.print.attribute.standard.Chromaticity");
		reader.framework.add("javax.print.attribute.standard.Copies");
		reader.framework.add("javax.print.attribute.standard.Destination");
		reader.framework.add("javax.print.attribute.standard.Finishings");
		reader.framework.add("javax.print.attribute.standard.JobHoldUntil");
		reader.framework.add("javax.print.attribute.standard.JobName");
		reader.framework.add("javax.print.attribute.standard.JobPriority");
		reader.framework.add("javax.print.attribute.standard.Media");
		reader.framework.add("javax.print.attribute.standard.MediaPrintableArea");
		reader.framework.add("javax.print.attribute.standard.MediaSize");
		reader.framework.add("javax.print.attribute.standard.MediaSizeName");
		reader.framework.add("javax.print.attribute.standard.MediaTray");
		reader.framework.add("javax.print.attribute.standard.NumberUp");
		reader.framework.add("javax.print.attribute.standard.OrientationRequested");
		reader.framework.add("javax.print.attribute.standard.PageRanges");
		reader.framework.add("javax.print.attribute.standard.PresentationDirection");
		reader.framework.add("javax.print.attribute.standard.PrinterResolution");
		reader.framework.add("javax.print.attribute.standard.PrintQuality");
		reader.framework.add("javax.print.attribute.standard.SheetCollate");
		reader.framework.add("javax.print.attribute.standard.Sides");
		reader.framework.add("javax.print.Doc");
		reader.framework.add("javax.print.DocFlavor");
		reader.framework.add("javax.print.DocFlavor$SERVICE_FORMATTED");
		reader.framework.add("javax.print.DocPrintJob");
		reader.framework.add("javax.print.event.PrintJobAdapter");
		reader.framework.add("javax.print.event.PrintJobEvent");
		reader.framework.add("javax.print.event.PrintJobListener");
		reader.framework.add("javax.print.PrintException");
		reader.framework.add("javax.print.PrintService");
		reader.framework.add("javax.print.PrintServiceLookup");
		reader.framework.add("javax.print.SimpleDoc");
		reader.framework.add("javax.print.StreamPrintService");
		reader.framework.add("javax.print.StreamPrintServiceFactory");
		reader.framework.add("javax.swing.AbstractButton");
		reader.framework.add("javax.swing.AbstractListModel");
		reader.framework.add("javax.swing.border.BevelBorder");
		reader.framework.add("javax.swing.border.Border");
		reader.framework.add("javax.swing.BorderFactory");
		reader.framework.add("javax.swing.ComboBoxEditor");
		reader.framework.add("javax.swing.DefaultListCellRenderer");
		reader.framework.add("javax.swing.DefaultListModel");
		reader.framework.add("javax.swing.event.ChangeEvent");
		reader.framework.add("javax.swing.event.ChangeListener");
		reader.framework.add("javax.swing.event.DocumentEvent");
		reader.framework.add("javax.swing.event.DocumentListener");
		reader.framework.add("javax.swing.event.ListSelectionEvent");
		reader.framework.add("javax.swing.event.ListSelectionListener");
		reader.framework.add("javax.swing.event.TreeModelEvent");
		reader.framework.add("javax.swing.event.TreeModelListener");
		reader.framework.add("javax.swing.event.TreeSelectionEvent");
		reader.framework.add("javax.swing.event.TreeSelectionListener");
		reader.framework.add("javax.swing.Icon");
		reader.framework.add("javax.swing.ImageIcon");
		reader.framework.add("javax.swing.JButton");
		reader.framework.add("javax.swing.JCheckBox");
		reader.framework.add("javax.swing.JComboBox");
		reader.framework.add("javax.swing.JComponent");
		reader.framework.add("javax.swing.JDialog");
		reader.framework.add("javax.swing.JFrame");
		reader.framework.add("javax.swing.JLabel");
		reader.framework.add("javax.swing.JList");
		reader.framework.add("javax.swing.JMenu");
		reader.framework.add("javax.swing.JMenuBar");
		reader.framework.add("javax.swing.JMenuItem");
		reader.framework.add("javax.swing.JOptionPane");
		reader.framework.add("javax.swing.JPanel");
		reader.framework.add("javax.swing.JProgressBar");
		reader.framework.add("javax.swing.JRootPane");
		reader.framework.add("javax.swing.JScrollPane");
		reader.framework.add("javax.swing.JSeparator");
		reader.framework.add("javax.swing.JSplitPane");
		reader.framework.add("javax.swing.JTabbedPane");
		reader.framework.add("javax.swing.JTextArea");
		reader.framework.add("javax.swing.JTextField");
		reader.framework.add("javax.swing.JTree");
		reader.framework.add("javax.swing.ListCellRenderer");
		reader.framework.add("javax.swing.ListModel");
		reader.framework.add("javax.swing.ListSelectionModel");
		reader.framework.add("javax.swing.ScrollPaneConstants");
		reader.framework.add("javax.swing.SwingConstants");
		reader.framework.add("javax.swing.SwingUtilities");
		reader.framework.add("javax.swing.text.JTextComponent");
		reader.framework.add("javax.swing.ToolTipManager");
		reader.framework.add("javax.swing.tree.DefaultTreeCellRenderer");
		reader.framework.add("javax.swing.tree.TreeCellRenderer");
		reader.framework.add("javax.swing.tree.TreeModel");
		reader.framework.add("javax.swing.tree.TreePath");
		reader.framework.add("javax.swing.UIManager");
		reader.framework.add("net.roydesign.app.AboutJMenuItem");
		reader.framework.add("net.roydesign.app.Application");
		reader.framework.add("net.roydesign.app.QuitJMenuItem");
		reader.framework.add("net.roydesign.event.ApplicationEvent");
		reader.framework.add("netscape.javascript.JSObject");
		reader.framework.add("org.apache.batik.dom.GenericDOMImplementation");
		reader.framework.add("org.apache.batik.svggen.SVGGraphics2D");
		reader.framework.add("org.hamcrest.BaseMatcher");
		reader.framework.add("org.hamcrest.core.CombinableMatcher");
		reader.framework.add("org.hamcrest.core.CombinableMatcher$CombinableBothMatcher");
		reader.framework.add("org.hamcrest.core.CombinableMatcher$CombinableEitherMatcher");
		reader.framework.add("org.hamcrest.CoreMatchers");
		reader.framework.add("org.hamcrest.Description");
		reader.framework.add("org.hamcrest.Factory");
		reader.framework.add("org.hamcrest.Matcher");
		reader.framework.add("org.hamcrest.MatcherAssert");
		reader.framework.add("org.hamcrest.SelfDescribing");
		reader.framework.add("org.hamcrest.StringDescription");
		reader.framework.add("org.hamcrest.TypeSafeMatcher");
		reader.framework.add("org.jdesktop.layout.Baseline");
		reader.framework.add("org.jdesktop.layout.LayoutStyle");
		reader.framework.add("org.w3c.dom.Document");
		reader.framework.add("org.w3c.dom.DocumentType");
		reader.framework.add("org.w3c.dom.DOMImplementation");
		reader.framework.add("org.xml.sax.Attributes");
		reader.framework.add("org.xml.sax.ContentHandler");
		reader.framework.add("org.xml.sax.DTDHandler");
		reader.framework.add("org.xml.sax.EntityResolver");
		reader.framework.add("org.xml.sax.ErrorHandler");
		reader.framework.add("org.xml.sax.helpers.DefaultHandler");
		reader.framework.add("org.xml.sax.helpers.XMLReaderFactory");
		reader.framework.add("org.xml.sax.InputSource");
		reader.framework.add("org.xml.sax.SAXException");
		reader.framework.add("org.xml.sax.SAXParseException");
		reader.framework.add("org.xml.sax.XMLReader");
		reader.framework.add("quicktime.app.view.GraphicsImporterDrawer");
		reader.framework.add("quicktime.app.view.QTImageProducer");
		reader.framework.add("quicktime.QTSession");
		reader.framework.add("quicktime.std.image.GraphicsImporter");
		reader.framework.add("quicktime.util.QTHandle");
		reader.framework.add("quicktime.util.QTHandleRef");
		reader.framework.add("quicktime.util.QTUtils");
		reader.framework.add("sun.awt.CausedFocusEvent");
		reader.framework.add("sun.awt.CausedFocusEvent$Cause");
		reader.framework.add("sun.security.util.SecurityConstants");
		reader.framework.add("sun.tools.javac.Main");
		reader.framework.add("junit.ui.TestRunner$13");

	
		for (String filename : instanceFilenamesArray)
			if (filename.length() > 0)
				instances.add (reader.execute(filename));
		
		return instances;
	}	
	
//	public static final void main(String[] args) throws Exception
//	{				
//		Vector<Project> projectInstances = new Vector<Project>();
//		projectInstances = runProjectsReading();
//		runPackageClassCombinationExport(projectInstances,false);
//		runClassDependencyCombinationExport(projectInstances);
////	
//		runLNSPExperiment("(((x * z) - (28.872247471852205 + y)) - (-20.769863672186244))");
////		runMOJOComparison("data\\Experiment\\LNSInterpretation\\junit-4.13.006022021213134.comb", "data\\Experiment\\LNSInterpretation\\junit-4.13.006022021213134.comb","-fm");	
////		runMOJOComparison("data\\Experiment\\PkgClsComb\\junit-4.13.006022021213134.comb ", "data\\Experiment\\LNSInterpretation\\junit-4.13.006022021213134.comb", "-fm");
//	
////		runProjectOverallAnalisis(projectInstances);		
////		runProjectDependencyExport(projectInstances);
//		
//		
//		}	
		
		
		
public static final void main(String[] args) throws Exception
	{				
				
//		List<String> instanceFilenames = new ArrayList<String>();
//		instanceFilenames.add(dataset.getNome());
//		
//		Vector<Project> instances = new Vector<Project>();
//		instances.addAll(ProjectLoader.readJarInstances(instanceFilenames));
		
		List<String> packageClassCombinationFilenames = new ArrayList<String>();
//		packageClassCombinationFilenames = ProjectLoader.runPackageClassCombinationExport(instances,false);
		packageClassCombinationFilenames.add ("data//Experiment//PkgClsComb//jodamoney-1.0.121022021133253.comb");
		
		
		List<String> classDependencyCombinationFilenames = new ArrayList<String>();
//		classDependencyCombinationFilenames = ProjectLoader.runClassDependencyCombinationExport(instances);
		classDependencyCombinationFilenames.add("data//Experiment//ClsDepComb//jodamoney-1.0.121022021133212.comb");
		
		
		LNSParameterTest LNSP = new LNSParameterTest();
		
		File f1 = new File(classDependencyCombinationFilenames.get(0));
		File[] instanceFiles = new File[1];
		instanceFiles[0]= f1;
		
		
//		System.out.println (arvore.getExpressao());
		List<String> lnsExperimentFilenames = new ArrayList<String>();
		try {
			lnsExperimentFilenames = LNSP.runExperiment("(((x) - (28.872247471852205 + y)) - (-20.769863672186244))",instanceFiles);
		} catch (InstanceParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(runMOJOComparison(packageClassCombinationFilenames.get(0), lnsExperimentFilenames.get(0),"-fm"));			
	}

	private static void runProjectOverallAnalisis(Vector<Project> projectsInstances) throws Exception {
		FileOutputStream fos = new FileOutputStream("data\\output" + getStringTime() + ".txt" );
		OutputStreamWriter bw = new OutputStreamWriter(fos);
		
		for (int i = 0; i < projectsInstances.size(); i++)
		{	
			bw.write(System.getProperty("line.separator"));
			bw.write(System.getProperty("line.separator"));
			String s1 = projectsInstances.get(i).getName() + "\t" + "ClassCount: " + "\t" +  projectsInstances.get(i).getClassCount() + "\t" +"MQ:" +"\t" +new MQCalculator(projectsInstances.get(i)).calculateModularizationQuality();
			bw.write("+" + s1 + System.getProperty("line.separator"));
			bw.flush();
			System.out.println(s1);
			for (ProjectPackage projectPackage : projectsInstances.get(i).getPackages()) {
				String s2 = projectPackage.getName();
				bw.write(s2 + System.getProperty("line.separator"));
				bw.flush();
				System.out.println(projectPackage.getName());	
			}
		}
		bw.close();
	}
	
	private static Vector<Project> runProjectsReading() throws Exception {
		//
		/*setting the folder for app.jar file after the convertion from .apk to .jar */
		File jarFilesFolder = new File("data\\Experiment\\JAR-Input\\"); // current directory
			
		/*starting the extraction of dependency relationship */
		try {
			/*loading the files from the specified folder for the jar files folder*/
			File[] files = jarFilesFolder.listFiles(new FilenameFilter(){
				
				/*filtering the files to guarantee that only .jar files will be listed */
				@Override
				public boolean accept(File dir, String name) {
					boolean result;
					if(name.endsWith(".jar")){
						result=true;
					}
					else{
						result=false;
					}
					return result;
				}
			});
			/*looping throw the files to add the .jar files*/
			for (File file : files) {
				if (!file.isDirectory()) {
					instanceFilenames.add(file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*creating a vector of project to load the instance jar file data*/
		Vector<Project> instances = new Vector<Project>();
		instances.addAll(readJarInstances(instanceFilenames));
		
		return instances;
	}
	
	private static void runLNSPExperiment(String objectiveEquation) throws InstanceParseException, IOException {
		LNSParameterTest LNSP = new LNSParameterTest();
		LNSP.runExperiment(objectiveEquation);
	}
	
	
	
	private static Vector<String> runClassDependencyCombinationExport(Vector<Project> projectsInstances) throws InstanceParseException, IOException {
		Vector<String> files = new Vector<String>();
		
		for (int i = 0; i < projectsInstances.size(); i++){
			StringBuilder sb = new StringBuilder();		   
			for (ProjectClass projectClass : projectsInstances.get(i).getClasses()) {
				if (projectClass.getDependencyCount()==0) {
					sb.append( projectClass.getName() + " ");
					sb.append(System.lineSeparator());
				}
				for (Dependency dependencyProjectClass : projectClass.getDependencies()){
					sb.append( projectClass.getName() + "  "  + dependencyProjectClass.getElementName());
					sb.append(System.lineSeparator());			
				}
			}
		   			
		    File file = new File("data\\Experiment\\ClsDepComb\\" + projectsInstances.get(i).getName() + getStringTime()+ ".comb");
		    files.add(file.getName());
		    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    try {
		        writer.write(sb.toString());	    
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   
			} finally {
				writer.close();
			}
		}	
		
		return files;
	}
	
	private static Vector<String> runPackageClassCombinationExport(Vector<Project> projectsInstances, boolean projectPackageName) throws InstanceParseException, IOException {
		Vector<String> files = new Vector<String>();
		
		for (int i = 0; i < projectsInstances.size(); i++){
			StringBuilder sb = new StringBuilder();
		   int j = 1;
		    for(ProjectPackage projectPackage: projectsInstances.get(i).getPackages()) {
		    	for(ProjectClass projectClass1: projectsInstances.get(i).getClasses(projectPackage)) {
//		    		if (projectClass1.getDependencyCount()>0) {
		    			if (projectPackageName) {
			    			sb.append("contain " + projectPackage.getName() + " " + projectClass1.getName());		
			    		}
			    		else {
			    			sb.append("contain " + "PKG" + j + " " + projectClass1.getName());
			    		}		
						sb.append(System.lineSeparator());
			    		j++;				    			
//		    		}		    							
		    	}
		    }
			
		    File file = new File("data//Experiment//PkgClsComb//"+ projectsInstances.get(i).getName() + getStringTime()+ ".comb");
		    files.add(file.getName());
		    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    try {
		        writer.write(sb.toString());	    
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   
			} finally {
				writer.close();
			}
		}
		return files;
	}
	
	private static double runMOJOComparison(String file1, String file2, String param) throws InstanceParseException, IOException {
//		System.out.println("runMOJOComparison");
		String[] args1 = new String[3];
		args1[0] = file1;
		args1[1] = file2;
		args1[2] = param; 
		return MoJo.MojoFM(args1);
	}

	
//	private static void runMOJOComparison(String file1, String file2, String param) throws InstanceParseException, IOException {
//		System.out.println("runMOJOComparison");
//		String[] args1 = new String[3];
//		args1[0] = file1;
//		args1[1] = file2;
//		args1[2] = param; 
//		MoJo.main(args1);
//	}
	
	private static void runMOJOComparison(String file1, String file2) throws InstanceParseException, IOException {
		System.out.println("runMOJOComparison");
		String[] args1 = new String[2];
		args1[0] = file1;
		args1[1] = file2; 
		MoJo.main(args1);
	}
	
	
	private static String getStringTime() {
		Calendar data;
		data = Calendar.getInstance();
		int segundo = data.get(Calendar.SECOND);
        int minuto = data.get(Calendar.MINUTE);
        int hora = data.get(Calendar.HOUR_OF_DAY);
        int dia = data.get(Calendar.DAY_OF_MONTH);	
        int mes = data.get(Calendar.MONTH);;	
        int ano = data.get(Calendar.YEAR);;		
		return  String.format("%02d", dia) + String.format("%02d", mes) + String.format("%04d", ano) +String.format("%02d", hora) + String.format("%02d", minuto) + String.format("%02d", segundo);
	}
}