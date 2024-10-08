// Generated from BythonParser.g4 by ANTLR 4.3
package oo2.redictado.antlr4;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BythonParser}.
 */
public interface BythonParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BythonParser#functionDecl}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDecl(@NotNull BythonParser.FunctionDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#functionDecl}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDecl(@NotNull BythonParser.FunctionDeclContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void enterElseClause(@NotNull BythonParser.ElseClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void exitElseClause(@NotNull BythonParser.ElseClauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void enterValueExpression(@NotNull BythonParser.ValueExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void exitValueExpression(@NotNull BythonParser.ValueExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void enterImportStatement(@NotNull BythonParser.ImportStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void exitImportStatement(@NotNull BythonParser.ImportStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#moduleName}.
	 * @param ctx the parse tree
	 */
	void enterModuleName(@NotNull BythonParser.ModuleNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#moduleName}.
	 * @param ctx the parse tree
	 */
	void exitModuleName(@NotNull BythonParser.ModuleNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void enterListLiteral(@NotNull BythonParser.ListLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void exitListLiteral(@NotNull BythonParser.ListLiteralContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(@NotNull BythonParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(@NotNull BythonParser.ProgramContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#methodDecl}.
	 * @param ctx the parse tree
	 */
	void enterMethodDecl(@NotNull BythonParser.MethodDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#methodDecl}.
	 * @param ctx the parse tree
	 */
	void exitMethodDecl(@NotNull BythonParser.MethodDeclContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(@NotNull BythonParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(@NotNull BythonParser.ReturnStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#tryExceptStatement}.
	 * @param ctx the parse tree
	 */
	void enterTryExceptStatement(@NotNull BythonParser.TryExceptStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#tryExceptStatement}.
	 * @param ctx the parse tree
	 */
	void exitTryExceptStatement(@NotNull BythonParser.TryExceptStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(@NotNull BythonParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(@NotNull BythonParser.ArgumentListContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#dictLiteral}.
	 * @param ctx the parse tree
	 */
	void enterDictLiteral(@NotNull BythonParser.DictLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#dictLiteral}.
	 * @param ctx the parse tree
	 */
	void exitDictLiteral(@NotNull BythonParser.DictLiteralContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull BythonParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull BythonParser.StatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#objectProperty}.
	 * @param ctx the parse tree
	 */
	void enterObjectProperty(@NotNull BythonParser.ObjectPropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#objectProperty}.
	 * @param ctx the parse tree
	 */
	void exitObjectProperty(@NotNull BythonParser.ObjectPropertyContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(@NotNull BythonParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(@NotNull BythonParser.BlockContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#methodParamList}.
	 * @param ctx the parse tree
	 */
	void enterMethodParamList(@NotNull BythonParser.MethodParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#methodParamList}.
	 * @param ctx the parse tree
	 */
	void exitMethodParamList(@NotNull BythonParser.MethodParamListContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#aliasName}.
	 * @param ctx the parse tree
	 */
	void enterAliasName(@NotNull BythonParser.AliasNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#aliasName}.
	 * @param ctx the parse tree
	 */
	void exitAliasName(@NotNull BythonParser.AliasNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExpression(@NotNull BythonParser.LambdaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExpression(@NotNull BythonParser.LambdaExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierList(@NotNull BythonParser.IdentifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierList(@NotNull BythonParser.IdentifierListContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull BythonParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull BythonParser.ExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#classMember}.
	 * @param ctx the parse tree
	 */
	void enterClassMember(@NotNull BythonParser.ClassMemberContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#classMember}.
	 * @param ctx the parse tree
	 */
	void exitClassMember(@NotNull BythonParser.ClassMemberContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#classDecl}.
	 * @param ctx the parse tree
	 */
	void enterClassDecl(@NotNull BythonParser.ClassDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#classDecl}.
	 * @param ctx the parse tree
	 */
	void exitClassDecl(@NotNull BythonParser.ClassDeclContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(@NotNull BythonParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(@NotNull BythonParser.AssignmentContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#exceptionList}.
	 * @param ctx the parse tree
	 */
	void enterExceptionList(@NotNull BythonParser.ExceptionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#exceptionList}.
	 * @param ctx the parse tree
	 */
	void exitExceptionList(@NotNull BythonParser.ExceptionListContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(@NotNull BythonParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(@NotNull BythonParser.ForStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(@NotNull BythonParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(@NotNull BythonParser.IfStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#collection}.
	 * @param ctx the parse tree
	 */
	void enterCollection(@NotNull BythonParser.CollectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#collection}.
	 * @param ctx the parse tree
	 */
	void exitCollection(@NotNull BythonParser.CollectionContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#simpleAssignment}.
	 * @param ctx the parse tree
	 */
	void enterSimpleAssignment(@NotNull BythonParser.SimpleAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#simpleAssignment}.
	 * @param ctx the parse tree
	 */
	void exitSimpleAssignment(@NotNull BythonParser.SimpleAssignmentContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#elifClause}.
	 * @param ctx the parse tree
	 */
	void enterElifClause(@NotNull BythonParser.ElifClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#elifClause}.
	 * @param ctx the parse tree
	 */
	void exitElifClause(@NotNull BythonParser.ElifClauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(@NotNull BythonParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(@NotNull BythonParser.WhileStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#indexAccess}.
	 * @param ctx the parse tree
	 */
	void enterIndexAccess(@NotNull BythonParser.IndexAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#indexAccess}.
	 * @param ctx the parse tree
	 */
	void exitIndexAccess(@NotNull BythonParser.IndexAccessContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#tupleLiteral}.
	 * @param ctx the parse tree
	 */
	void enterTupleLiteral(@NotNull BythonParser.TupleLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#tupleLiteral}.
	 * @param ctx the parse tree
	 */
	void exitTupleLiteral(@NotNull BythonParser.TupleLiteralContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#setLiteral}.
	 * @param ctx the parse tree
	 */
	void enterSetLiteral(@NotNull BythonParser.SetLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#setLiteral}.
	 * @param ctx the parse tree
	 */
	void exitSetLiteral(@NotNull BythonParser.SetLiteralContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#compoundAssignment}.
	 * @param ctx the parse tree
	 */
	void enterCompoundAssignment(@NotNull BythonParser.CompoundAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#compoundAssignment}.
	 * @param ctx the parse tree
	 */
	void exitCompoundAssignment(@NotNull BythonParser.CompoundAssignmentContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#implicitAssignment}.
	 * @param ctx the parse tree
	 */
	void enterImplicitAssignment(@NotNull BythonParser.ImplicitAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#implicitAssignment}.
	 * @param ctx the parse tree
	 */
	void exitImplicitAssignment(@NotNull BythonParser.ImplicitAssignmentContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#callableExpression}.
	 * @param ctx the parse tree
	 */
	void enterCallableExpression(@NotNull BythonParser.CallableExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#callableExpression}.
	 * @param ctx the parse tree
	 */
	void exitCallableExpression(@NotNull BythonParser.CallableExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#indexAssignment}.
	 * @param ctx the parse tree
	 */
	void enterIndexAssignment(@NotNull BythonParser.IndexAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#indexAssignment}.
	 * @param ctx the parse tree
	 */
	void exitIndexAssignment(@NotNull BythonParser.IndexAssignmentContext ctx);

	/**
	 * Enter a parse tree produced by {@link BythonParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void enterMethodCall(@NotNull BythonParser.MethodCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link BythonParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void exitMethodCall(@NotNull BythonParser.MethodCallContext ctx);
}