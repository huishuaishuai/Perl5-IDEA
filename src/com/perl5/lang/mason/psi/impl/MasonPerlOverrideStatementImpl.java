/*
 * Copyright 2015 Alexandr Evstigneev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.perl5.lang.mason.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.perl5.lang.mason.psi.MasonPerlOverrideStatement;
import com.perl5.lang.perl.parser.moose.psi.PerlMooseOverrideStatement;
import com.perl5.lang.perl.parser.moose.psi.PerlMooseOverrideStatementImpl;
import com.perl5.lang.perl.parser.moose.stubs.override.PerlMooseOverrideStub;
import com.perl5.lang.perl.psi.PerlString;
import com.perl5.lang.perl.psi.PerlSubNameElement;
import com.perl5.lang.perl.psi.PsiPerlBlock;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 03.01.2016.
 */
public class MasonPerlOverrideStatementImpl extends PerlMooseOverrideStatementImpl implements MasonPerlOverrideStatement
{
	public MasonPerlOverrideStatementImpl(@NotNull ASTNode node)
	{
		super(node);
	}

	public MasonPerlOverrideStatementImpl(@NotNull PerlMooseOverrideStub stub, @NotNull IStubElementType nodeType)
	{
		super(stub, nodeType);
	}

	@Override
	@NotNull
	public PsiPerlBlock getBlock()
	{
		return findNotNullChildByClass(PsiPerlBlock.class);
	}

	@Override
	public PsiElement getSubNameElement()
	{
		return PsiTreeUtil.getChildOfType(this, PerlSubNameElement.class);
	}

	@Override
	protected String getSubNameHeavy()
	{
		PsiElement subNameElement = getSubNameElement();
		return subNameElement == null ? null : subNameElement.getNode().getText();
	}

	@Override
	public PsiElement setName(@NotNull String name) throws IncorrectOperationException
	{
		if (name.isEmpty())
		{
			throw new IncorrectOperationException("You can't set an empty method name");
		}

		PsiElement nameIdentifier = getNameIdentifier();
		if (nameIdentifier instanceof LeafPsiElement)
		{
			((LeafPsiElement) nameIdentifier).replaceWithText(name);
		}

		return this;
	}
}
