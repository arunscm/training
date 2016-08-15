/*
 * #%L
 * broadleaf-common-thymeleaf
 * %%
 * Copyright (C) 2009 - 2016 Broadleaf Commerce
 * %%
 * Licensed under the Broadleaf Fair Use License Agreement, Version 1.0
 * (the "Fair Use License" located  at http://license.broadleafcommerce.org/fair_use_license-1.0.txt)
 * unless the restrictions on use therein are violated and require payment to Broadleaf in which case
 * the Broadleaf End User License Agreement (EULA), Version 1.1
 * (the "Commercial License" located at http://license.broadleafcommerce.org/commercial_license-1.1.txt)
 * shall apply.
 * 
 * Alternatively, the Commercial License may be replaced with a mutually agreed upon license (the "Custom License")
 * between you and Broadleaf Commerce. You may not use this file except in compliance with the applicable license.
 * #L%
 */
package org.broadleafcommerce.common.web.dialect;

import org.broadleafcommerce.common.web.domain.BroadleafThymeleafContext;
import org.broadleafcommerce.common.web.domain.BroadleafThymeleafContextImpl;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Map;

public abstract class AbstractBroadleafTagTextModifierProcessor extends AbstractAttributeTagProcessor {

    protected AbstractBroadleafTagTextModifierProcessor(String name, boolean removeAttribute, int precedence) {
        super(TemplateMode.HTML, "blc", null, false, name, true, precedence, removeAttribute);
    }

    protected AbstractBroadleafTagTextModifierProcessor(String name, int precedence) {
        this(name, true, precedence);
    }

    protected AbstractBroadleafTagTextModifierProcessor(String name) {
        this(name, 1000);
    }
    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue, IElementTagStructureHandler structureHandler) {
        BroadleafThymeleafContext blcContext = new BroadleafThymeleafContextImpl(context, structureHandler);
        String tagName = tag.getElementCompleteName();
        Map<String, String> tagAttributes = tag.getAttributeMap();
        String newText = getTagText(tagName, tagAttributes, attributeName.getAttributeName(), attributeValue, blcContext);
        if (newText != null) {
            structureHandler.setBody(newText, textShouldBeProcessed());
        }

    }

    protected boolean textShouldBeProcessed() {
        return false;
    }

    protected abstract String getTagText(String tagName, Map<String, String> tagAttributes, String attributeName, String attributeValue, BroadleafThymeleafContext context);

}