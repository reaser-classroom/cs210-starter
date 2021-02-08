package grade;

import org.junit.jupiter.api.BeforeAll;

public class Module1 extends SQLModule {
	@BeforeAll
	public static void setup() {
		module_tag = "M1";

		query_data = new Object[][]{
			// CREATE TABLE
			{ true,  "m1_table01", "CREATE TABLE m1_table01 (id INTEGER PRIMARY, name STRING, flag BOOLEAN)", null },
			{ false, "m1_table01", "CREATE TABLE m1_table01 (id INTEGER PRIMARY, name STRING, flag BOOLEAN)", "the table name must not already be in use" },

			// CREATE TABLE: CASE, WHITESPACE
			{ true,  "m1_table02", "create table m1_table02 (ID integer primary, NAME string, flag BOOLEAN)", "lowercase keywords and uppercase table names are allowed" },
			{ true,  "m1_table03", " CREATE TABLE m1_table03 (id INTEGER PRIMARY, name STRING, flag BOOLEAN) ", "untrimmed whitespace is allowed" },
			{ true,  "m1_table04", "CREATE  TABLE  m1_table04  (id INTEGER PRIMARY, name STRING, flag BOOLEAN)", "excess internal whitespace is allowed" },
			{ true,  "m1_table05", "CREATE TABLE m1_table05 ( id INTEGER PRIMARY , name STRING , flag BOOLEAN )", "excess internal whitespace is allowed" },
			{ true,  "m1_table06", "CREATE TABLE m1_table06 (id INTEGER PRIMARY,name STRING,flag BOOLEAN)", "whitespace around punctuation is not required" },
			{ false, "m1_table07", "CREATETABLE m1_table07 (id INTEGERPRIMARY, name STRING, flag BOOLEAN)", "whitespace between keywords is required " },
			{ false, "m1_table08", "CREATE TABLEm1_table08 (idINTEGER PRIMARY, nameSTRING, flag BOOLEAN)", "whitespace between keywords and names is required" },

			// CREATE TABLE: NAMES, KEYWORDS, PUNCTUATION
			{ true,  "t", "CREATE TABLE t (i INTEGER PRIMARY, n STRING, f BOOLEAN)", "names can be 1 letter" },
			{ true,  "m1_table10_____", "CREATE TABLE m1_table10_____ (n23456789012345 INTEGER PRIMARY)", "names can be up to 15 characters" },
			{ false, "m1_table11______", "CREATE TABLE m1_table11______ (n234567890123456 INTEGER PRIMARY)", "names can be no more than 15 characters" },
			{ false, "1m_table12", "CREATE TABLE 1m_table12 (2id INTEGER PRIMARY, 3name STRING, 4flag BOOLEAN)", "a name cannot start with a number" },
			{ false, "_m1table13", "CREATE TABLE _m1table13 (_id INTEGER PRIMARY, _name STRING, _flag BOOLEAN)", "a name cannot start with an underscore" },
			{ false, "", "CREATE TABLE (id INTEGER PRIMARY, name STRING, flag BOOLEAN)", "the table name cannot be omitted" },
			{ false, "m1_table15", "CREATE m1_table15 (id INTEGER PRIMARY, name STRING, flag BOOLEAN)", "the TABLE keyword is required" },
			{ false, "m1_table16", "CREATE TABLE m1_table16 (id INTEGER PRIMARY name STRING flag BOOLEAN)", "the commas between definitions are required" },
			{ false, "m1_table17", "CREATE TABLE m1_table17 id INTEGER PRIMARY, name STRING, flag BOOLEAN", "the parentheses are required" },

			// CREATE TABLE: COLUMNS, PRIMARY
			{ false, "m1_table18", "CREATE TABLE m1_table18 ()", "there must be at least one column" },
			{ true,  "m1_table19", "CREATE TABLE m1_table19 (c1 INTEGER PRIMARY)", "a single column is allowed" },
			{ true,  "m1_table20", "CREATE TABLE m1_table20 (c1 INTEGER PRIMARY, c2 STRING)", "two columns are allowed" },
			{ true,  "m1_table21", "CREATE TABLE m1_table21 (c1 INTEGER PRIMARY, c2 INTEGER, c3 INTEGER, c4 INTEGER, c5 INTEGER, c6 INTEGER, c7 INTEGER, c8 INTEGER, c9 INTEGER, c10 INTEGER, c11 INTEGER, c12 INTEGER, c13 INTEGER, c14 INTEGER, c15 INTEGER)", "up to 15 columns are allowed" },
			{ false, "m1_table22", "CREATE TABLE m1_table22 (c1 INTEGER PRIMARY, c2 INTEGER, c3 INTEGER, c4 INTEGER, c5 INTEGER, c6 INTEGER, c7 INTEGER, c8 INTEGER, c9 INTEGER, c10 INTEGER, c11 INTEGER, c12 INTEGER, c13 INTEGER, c14 INTEGER, c15 INTEGER, c16 INTEGER)", "more than 15 columns are not allowed" },
			{ false, "m1_table23", "CREATE TABLE m1_table23 (id INTEGER PRIMARY, other STRING, other BOOLEAN)", "the column names cannot have duplicates" },
			{ false, "m1_table24", "CREATE TABLE m1_table24 (id INTEGER, name STRING, flag BOOLEAN)", "there must be a primary column" },
			{ false, "m1_table25", "CREATE TABLE m1_table25 (id INTEGER PRIMARY, name STRING PRIMARY, flag BOOLEAN PRIMARY)", "there can be only one primary column" },
			{ true,  "m1_table26", "CREATE TABLE m1_table26 (id INTEGER, name STRING PRIMARY, flag BOOLEAN)", "the primary column need not be the first" },

			// DROP TABLE
			{ true,  "m1_table01", "DROP TABLE m1_table01", null },
			{ false, "m1_table01", "DROP TABLE m1_table01", "previously dropped table cannot be dropped again" },
			{ true,  "m1_table01", "CREATE TABLE m1_table01 (ps STRING PRIMARY)", "previously dropped table name can be reused" },

			// DROP TABLE: CASE, WHITESPACE
			{ true,  "m1_table02", "drop table m1_table02", "lowercase keywords and uppercase table names are allowed" },
			{ true,  "m1_table03", " DROP TABLE m1_table03 ", "untrimmed whitespace is allowed" },
			{ true,  "m1_table04", "DROP  TABLE  m1_table04", "excess internal whitespace is allowed" },
			{ false, "m1_table05", "DROPTABLE m1_table05", "whitespace between keywords is required " },
			{ false, "m1_table06", "DROP TABLEm1_table06", "whitespace between keywords and names is required" },

			// DROP TABLE: NAMES, KEYWORDS
			{ true,  "t", "DROP TABLE t", "names can be a single letter" },
			{ false, "", "DROP TABLE", "the table name cannot be omitted" },
			{ false, "m1_table17", "DROP m1_table17", "the TABLE keyword is required" },

			// SHOW TABLES
			{ true,  "_tables", "SHOW TABLES", null },
			{ true,  "_tables", "show tables", "lowercase keywords are allowed" },
			{ true,  "_tables", "  SHOW  TABLES  ", "excess internal whitespace and untrimmed whitespace is allowed" },
			{ false, "_tables", "SHOWTABLES", "whitespace between keywords is required " },
			{ false, "_tables", "SHOW", "the TABLES keyword is required" },

			// ROBUSTNESS
			{ false, null, "A MALFORMED QUERY", "a malformed query should be unrecognized" },
			{ true,  null, "ECHO \"Test1\"; ECHO \"Test2\"; ECHO \"Test3\"", "multiple semicolon-delimited queries are allowed" },
		};

		serial_data = new Object[][]{
			{ "m1_table01", 3, 0, "id", "name", "flag", "integer", "string", "boolean" },
			{ "m1_table01", 3, 0, "id", "name", "flag", "integer", "string", "boolean" },
			{ "m1_table02", 3, 0, "ID", "NAME", "flag", "integer", "string", "boolean" },
			{ "m1_table03", 3, 0, "id", "name", "flag", "integer", "string", "boolean" },
			{ "m1_table04", 3, 0, "id", "name", "flag", "integer", "string", "boolean" },
			{ "m1_table05", 3, 0, "id", "name", "flag", "integer", "string", "boolean" },
			{ "m1_table06", 3, 0, "id", "name", "flag", "integer", "string", "boolean" },
			null,
			null,
			{ "t", 3, 0, "i", "n", "f", "integer", "string", "boolean" },
			{ "m1_table10_____", 1, 0, "n23456789012345", "integer" },
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			{ "m1_table19", 1, 0, "c1", "integer" },
			{ "m1_table20", 2, 0, "c1", "c2", "integer", "string" },
			{ "m1_table21", 15, 0, "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15", "integer", "integer", "integer", "integer", "integer", "integer", "integer", "integer", "integer", "integer", "integer", "integer", "integer", "integer", "integer" },
			null,
			null,
			null,
			null,
			{ "m1_table26", 3, 1, "id", "name", "flag", "integer", "string", "boolean" },
			null,
			null,
			{ "m1_table01", 1, 0, "ps", "string" },
			null,
			null,
			null,
			{ "m1_table05", 3, 0, "id", "name", "flag", "integer", "string", "boolean" },
			{ "m1_table06", 3, 0, "id", "name", "flag", "integer", "string", "boolean" },
			null,
			null,
			null,
			{ "_tables", 2, 0, "table_name", "row_count", "string", "integer", "m1_table26", 0, "m1_table01", 0, "m1_table10_____", 0, "m1_table19", 0, "m1_table06", 0, "m1_table20", 0, "m1_table21", 0, "m1_table05", 0 },
			{ "_tables", 2, 0, "table_name", "row_count", "string", "integer", "m1_table26", 0, "m1_table01", 0, "m1_table10_____", 0, "m1_table19", 0, "m1_table06", 0, "m1_table20", 0, "m1_table21", 0, "m1_table05", 0 },
			{ "_tables", 2, 0, "table_name", "row_count", "string", "integer", "m1_table26", 0, "m1_table01", 0, "m1_table10_____", 0, "m1_table19", 0, "m1_table06", 0, "m1_table20", 0, "m1_table21", 0, "m1_table05", 0 },
			null,
			null,
			null,
			null
		};
	}
}