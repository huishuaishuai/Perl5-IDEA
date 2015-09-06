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

package com.perl5.lang.perl.psi.utils;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.perl5.lang.perl.psi.PerlStringContentElement;
import com.perl5.lang.perl.psi.PsiPerlStatement;
import com.perl5.lang.perl.psi.impl.PerlHeredocElementImpl;
import com.perl5.lang.perl.psi.references.PerlHeredocReference;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by hurricup on 09.08.2015.
 */
public class PerlPsiUtil
{
	/**
	 * Recursively searches for string content elements beginnign from specified PsiElement
	 *
	 * @param startWith PsiElement to start from (inclusive)
	 * @return list of PerlStringContentElement
	 */
	public static Collection<PerlStringContentElement> findStringElments(PsiElement startWith)
	{
		ArrayList<PerlStringContentElement> result = new ArrayList<PerlStringContentElement>();
		findStringElments(startWith, result);
		return result;
	}

	/**
	 * Recursive searcher for string content elements
	 *
	 * @param startWith element to start with (inclusive)
	 * @param result    list to populate
	 */
	public static void findStringElments(PsiElement startWith, Collection<PerlStringContentElement> result)
	{
		while (startWith != null)
		{
			if (startWith instanceof PerlStringContentElement)
				result.add((PerlStringContentElement) startWith);

			if (startWith.getFirstChild() != null)
				findStringElments(startWith.getFirstChild(), result);

			startWith = startWith.getNextSibling();
		}
	}

	/**
	 * Searching for statement this element belongs
	 *
	 * @param element
	 * @return
	 */
	public static PsiPerlStatement getElementStatement(PsiElement element)
	{
		PsiElement currentStatement = PsiTreeUtil.getParentOfType(element, PerlHeredocElementImpl.class);

		if (currentStatement != null)    // we are in heredoc
		{
			PsiElement opener = PerlHeredocReference.getClosestHeredocOpener(currentStatement);

			if (opener == null)
				return null;

			return PsiTreeUtil.getParentOfType(opener, PsiPerlStatement.class);

		} else
			return PsiTreeUtil.getParentOfType(element, PsiPerlStatement.class);
	}

}
