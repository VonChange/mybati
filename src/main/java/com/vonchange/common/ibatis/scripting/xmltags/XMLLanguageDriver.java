/**
 *    Copyright 2009-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.vonchange.common.ibatis.scripting.xmltags;

import com.vonchange.common.ibatis.logging.Log;
import com.vonchange.common.ibatis.logging.LogFactory;
import com.vonchange.common.ibatis.mapping.SqlSource;
import com.vonchange.common.ibatis.parsing.PropertyParser;
import com.vonchange.common.ibatis.parsing.XNode;
import com.vonchange.common.ibatis.parsing.XPathParser;
import com.vonchange.common.ibatis.scripting.LanguageDriver;
import com.vonchange.common.ibatis.scripting.defaults.RawSqlSource;
import com.vonchange.common.ibatis.session.Configuration;

/**
 * @author Eduardo Macarron
 */
public class XMLLanguageDriver implements LanguageDriver {
    private static final Log log = LogFactory.getLog(XMLLanguageDriver.class);


  @Override
  public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
    XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
    return builder.parseScriptNode();
  }

  @Override
  public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
      if (script.startsWith("<script>")) {
          XPathParser parser = new XPathParser(script, false, configuration.getVariables(), null);
          return createSqlSource(configuration, parser.evalNode("/script"), parameterType);
      } else {
          // issue #127
          script = PropertyParser.parse(script, configuration.getVariables());
          TextSqlNode textSqlNode = new TextSqlNode(script);
          if (textSqlNode.isDynamic()) {
              return new DynamicSqlSource(configuration, textSqlNode);
          } else {
              return new RawSqlSource(configuration, script, parameterType);
          }
      }
  }

}
