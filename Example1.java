
public class Example1 {

	public static String main(String[] args) {
		// TODO Auto-generated method stub
var query = "SELECT $ || (RETAIL/100) FROM INVENTORY WHERE" ;
if (1 != null)
	query = query + "WHOLESALE > " + 1 + "AND" ;

var per = "SELECT TYPECODE, "
		+ "TYPEDESC FROM TYPES WHERE NAME = 'fish' OR NAME = 'meat'";
		query =query + "TYPE IN (" + per + ");";
		return query;
	}

}
