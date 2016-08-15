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
import org.broadleafcommerce.common.web.domain.BroadleafThymeleafModel;
import org.broadleafcommerce.common.web.domain.BroadleafThymeleafModelImpl;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Map;


public abstract class AbstractBroadleafTagReplacementProcessor extends AbstractElementTagProcessor {

    public AbstractBroadleafTagReplacementProcessor(String tagName, int precedence) {
        super(TemplateMode.HTML, "blc", tagName, true, null, false, precedence);
    }

    public AbstractBroadleafTagReplacementProcessor(String tagName) {
        this(tagName, 10000);
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler structureHandler) {
        BroadleafThymeleafContext blcContext = new BroadleafThymeleafContextImpl(context, structureHandler);
        String tagName = tag.getElementCompleteName();
        Map<String, String> tagAttributes = tag.getAttributeMap();
        BroadleafThymeleafModel blcModel = getReplacementModel(tagName, tagAttributes, blcContext);
        if (blcModel != null) {
            structureHandler.replaceWith(((BroadleafThymeleafModelImpl) blcModel).getModel(), replacementNeedsProcessing());
        } else {
            structureHandler.removeTags();
        }
    }

    protected boolean replacementNeedsProcessing() {
        return false;
    }
    protected abstract BroadleafThymeleafModel getReplacementModel(String tagName, Map<String, String> tagAttributes, BroadleafThymeleafContext context);

}