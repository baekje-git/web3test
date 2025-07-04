package ever.support.log;

import net.sf.log4jdbc.Slf4jSpyLogDelegator;
import net.sf.log4jdbc.Spy;
import net.sf.log4jdbc.tools.LoggingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4JdbcCustomFormatter extends Slf4jSpyLogDelegator {
	private static final Logger logger = LoggerFactory.getLogger(Log4JdbcCustomFormatter.class);

	private LoggingType loggingType = LoggingType.DISABLED;

	private String margin = "";

	private String sqlPrefix = "::::::::::: SQL BDGIN :::::::::::";

	private String sqlSuffix = "::::::::::: SQL END   :::::::::::";

	public LoggingType getLoggingType() {
		return this.loggingType;
	}

	public void setLoggingType(LoggingType loggingType) {
		this.loggingType = loggingType;
	}

	public int getMargin() {
		return this.margin.length();
	}

	public void setMargin(int n) {
		this.margin = String.format("%1$" + n + "s", new Object[] { "" });
	}

	public String getSqlPrefix() {
		return this.sqlPrefix;
	}

	public void setSqlPrefix(String sqlPrefix) {
		this.sqlPrefix = sqlPrefix;
	}

	public String getSqlSuffix() {
		return this.sqlSuffix;
	}

	public void setSqlSuffix(String sqlSuffix) {
		this.sqlSuffix = sqlSuffix;
	}

	public String sqlOccured(Spy spy, String methodCall, String rawSql) {
		String sql = rawSql;
		sql = sql.trim();
		sql = sql.replaceAll("\t", "    ");
		sql = sql.replaceAll("\n", "\n" + this.margin);
		getSqlOnlyLogger().debug(
				"\n" + this.sqlPrefix + "\n\n" + this.margin + this.margin + sql + "\n\n" + this.sqlSuffix + "\n");
		return sql;
	}

	public String sqlOccured(Spy spy, String methodCall, String[] sqls) {
		String s = "";
		for (int i = 0; i < sqls.length; i++)
			s = String.valueOf(s) + sqlOccured(spy, methodCall, sqls[i]) + String.format("%n", new Object[0]);
		return s;
	}
}
