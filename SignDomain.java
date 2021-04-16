package it.unive.lisa.analysis.nonrelational.value.impl;

import it.unive.lisa.analysis.SemanticDomain.Satisfiability;
import it.unive.lisa.analysis.SemanticException;
import it.unive.lisa.analysis.nonrelational.value.BaseNonRelationalValueDomain;
import it.unive.lisa.analysis.nonrelational.value.NonRelationalValueDomain;
import it.unive.lisa.analysis.nonrelational.value.ValueEnvironment;
import it.unive.lisa.program.cfg.ProgramPoint;
import it.unive.lisa.symbolic.value.BinaryOperator;
import it.unive.lisa.symbolic.value.Constant;
import it.unive.lisa.symbolic.value.TernaryOperator;
import it.unive.lisa.symbolic.value.UnaryOperator;
import it.unive.lisa.symbolic.value.ValueExpression;

public class SignDomain extends BaseNonRelationalValueDomain<SignDomain> {
	
	enum Sign {
		PLUS, MINUS, ZERO, TOP, BOTTOM
	}
	private final Sign sign;
	
	public SignDomain() {
		this(Sign.TOP);
	}
	private SignDomain(Sign sign) {
		this.sign = sign;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sign == null) ? 0 : sign.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SignDomain other = (SignDomain) obj;
		if (sign != other.sign)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return sign.name();
	}
	@Override
	public SignDomain lub(SignDomain other) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SignDomain widening(SignDomain other) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean lessOrEqual(SignDomain other) throws SemanticException {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public SignDomain top() {
		return new SignDomain(Sign.TOP);
	}
	@Override
	public SignDomain bottom() {
		return new SignDomain(Sign.BOTTOM);
	}
	
	@Override
	public boolean isTop() {
		return this.sign == Sign.TOP;
	}
	
	@Override
	public boolean isBottom() {
		return this.sign == Sign.BOTTOM;
	}
	
	
	@Override
	public SignDomain eval(ValueExpression expression, ValueEnvironment<SignDomain> environment, ProgramPoint pp) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Satisfiability satisfies(ValueExpression expression, ValueEnvironment<SignDomain> environment,
			ProgramPoint pp) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String representation() {
		// TODO Auto-generated method stub
		return this.sign.name();
	}
	@Override
	protected SignDomain evalNullConstant(ProgramPoint pp) {
		return top();
	}
	@Override
	protected SignDomain evalNonNullConstant(Constant constant, ProgramPoint pp) {
	if (constant.getValue() instanceof Integer) {
		int c = (int) constant.getValue();
		if (c == 0)
			return new SignDomain(Sign.ZERO);
		else if (c > 0)
			return new SignDomain(Sign.PLUS);
		else 
			return new SignDomain(Sign.MINUS);
	}
		return top();
	}
	@Override
	protected SignDomain evalUnaryExpression(UnaryOperator operator, SignDomain arg, ProgramPoint pp) {
		switch(operator) {
		case NUMERIC_NEG:
			if (arg.sign == Sign.PLUS) {
				return new SignDomain(Sign.MINUS);
			} else if (arg.sign == Sign.ZERO) {
				return new SignDomain(Sign.ZERO);
			} else if (arg.sign == Sign.MINUS) {
				return new SignDomain(Sign.PLUS);
			}
		case LOGICAL_NOT:
		case STRING_LENGTH:
		case TYPEOF:
		default:
			return top();
		
		}
	}
	@Override
	protected SignDomain evalBinaryExpression(BinaryOperator operator, SignDomain left, SignDomain right,
			ProgramPoint pp) {
		switch(operator) {
		case COMPARISON_EQ:
		case COMPARISON_GE:
		case COMPARISON_GT:
		case COMPARISON_LE:
		case COMPARISON_LT:
		case COMPARISON_NE:
			return top();
		case LOGICAL_AND:
		case LOGICAL_OR:
			return top();
		case NUMERIC_ADD:
			switch(left.sign) {
		
			case MINUS:
				switch(right.sign) {
				
				case MINUS:
					return left;
				case PLUS:
					return top();
				case ZERO:
					return left;
				case TOP:
				case BOTTOM:
				default:
					return top();
				}	
			case PLUS:
                switch(right.sign) {
				
				case MINUS:
					return top();
				case PLUS:
					return left;
				case ZERO:
					return left;
				case TOP:
				case BOTTOM:
				default:
					return top();
				}
				
			case ZERO:
                switch(right.sign) {
				
				case MINUS:
					return right;
				case PLUS:
					return right;
				case ZERO:
					return left;
				case TOP:
				case BOTTOM:
				default:
					return top();
				}
	
			case BOTTOM:
			case TOP:
			default:
				return top();
			
			}
		
		case NUMERIC_DIV:
		case NUMERIC_MOD:
		case NUMERIC_MUL:
		case NUMERIC_SUB:
			return top();
		case STRING_CONCAT:
		case STRING_CONTAINS:
		case STRING_ENDS_WITH:
		case STRING_EQUALS:
		case STRING_INDEX_OF:
		case STRING_STARTS_WITH:
			return top();
		case TYPE_CAST:
		case TYPE_CHECK:
			return top();
		default:
			return top();
		}		
	}
	@Override
	protected SignDomain evalTernaryExpression(TernaryOperator operator, SignDomain left, SignDomain middle,
			SignDomain right, ProgramPoint pp) {
		switch(operator) {
		case STRING_REPLACE:
		case STRING_SUBSTRING:
		default:
			return top();
		}
	}
	@Override
	protected Satisfiability satisfiesAbstractValue(SignDomain value, ProgramPoint pp) {
		return Satisfiability.UNKNOWN;
	}
	@Override
	protected Satisfiability satisfiesNullConstant(ProgramPoint pp) {
		return Satisfiability.UNKNOWN;
	}
	@Override
	protected Satisfiability satisfiesNonNullConstant(Constant constant, ProgramPoint pp) {
		return Satisfiability.UNKNOWN;
	}
	@Override
	protected Satisfiability satisfiesUnaryExpression(UnaryOperator operator, SignDomain arg, ProgramPoint pp) {
		return Satisfiability.UNKNOWN;
	}
	@Override
	protected Satisfiability satisfiesBinaryExpression(BinaryOperator operator, SignDomain left, SignDomain right,
			ProgramPoint pp) {
		return Satisfiability.UNKNOWN;
	}
	@Override
	protected Satisfiability satisfiesTernaryExpression(TernaryOperator operator, SignDomain left, SignDomain middle,
			SignDomain right, ProgramPoint pp) {
		return Satisfiability.UNKNOWN;
	}
	@Override
	protected SignDomain lubAux(SignDomain other) throws SemanticException {
		return top();
	}
	@Override
	protected SignDomain wideningAux(SignDomain other) throws SemanticException {
		return lubAux(other);
	}
	@Override
	protected boolean lessOrEqualAux(SignDomain other) throws SemanticException {
		// TODO Auto-generated method stub
		return false;
	}

}
