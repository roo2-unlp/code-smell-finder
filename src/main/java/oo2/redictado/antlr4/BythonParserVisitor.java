// Generated from BythonParser.g4 by ANTLR 4.3
package oo2.redictado.antlr4;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BythonParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface BythonParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link BythonParser#functionDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDecl(@NotNull BythonParser.FunctionDeclContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#elseClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseClause(@NotNull BythonParser.ElseClauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#valueExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueExpression(@NotNull BythonParser.ValueExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#importStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportStatement(@NotNull BythonParser.ImportStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#moduleName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModuleName(@NotNull BythonParser.ModuleNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#listLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListLiteral(@NotNull BythonParser.ListLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(@NotNull BythonParser.ProgramContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#methodDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDecl(@NotNull BythonParser.MethodDeclContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(@NotNull BythonParser.ReturnStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#tryExceptStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryExceptStatement(@NotNull BythonParser.TryExceptStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(@NotNull BythonParser.ArgumentListContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#dictLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictLiteral(@NotNull BythonParser.DictLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull BythonParser.StatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#objectProperty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectProperty(@NotNull BythonParser.ObjectPropertyContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(@NotNull BythonParser.BlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#methodParamList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodParamList(@NotNull BythonParser.MethodParamListContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#aliasName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAliasName(@NotNull BythonParser.AliasNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#lambdaExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaExpression(@NotNull BythonParser.LambdaExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#identifierList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierList(@NotNull BythonParser.IdentifierListContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(@NotNull BythonParser.ExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#classMember}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassMember(@NotNull BythonParser.ClassMemberContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#classDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDecl(@NotNull BythonParser.ClassDeclContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(@NotNull BythonParser.AssignmentContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#exceptionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExceptionList(@NotNull BythonParser.ExceptionListContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(@NotNull BythonParser.ForStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(@NotNull BythonParser.IfStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#collection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollection(@NotNull BythonParser.CollectionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#simpleAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleAssignment(@NotNull BythonParser.SimpleAssignmentContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#elifClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElifClause(@NotNull BythonParser.ElifClauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(@NotNull BythonParser.WhileStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#indexAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexAccess(@NotNull BythonParser.IndexAccessContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#tupleLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupleLiteral(@NotNull BythonParser.TupleLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#setLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetLiteral(@NotNull BythonParser.SetLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#compoundAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundAssignment(@NotNull BythonParser.CompoundAssignmentContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#implicitAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImplicitAssignment(@NotNull BythonParser.ImplicitAssignmentContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#callableExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallableExpression(@NotNull BythonParser.CallableExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#indexAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexAssignment(@NotNull BythonParser.IndexAssignmentContext ctx);

	/**
	 * Visit a parse tree produced by {@link BythonParser#methodCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCall(@NotNull BythonParser.MethodCallContext ctx);
}