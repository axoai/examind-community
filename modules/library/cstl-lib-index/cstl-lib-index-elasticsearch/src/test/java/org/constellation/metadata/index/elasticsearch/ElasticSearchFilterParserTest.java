/*
 *    Constellation - An open source and standard compliant SDI
 *    http://www.constellation-sdi.org
 *
 * Copyright 2014 Geomatys.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.constellation.metadata.index.elasticsearch;

// J2SE dependencies

import org.apache.sis.xml.MarshallerPool;
import org.geotoolkit.csw.xml.v202.QueryConstraintType;
import org.geotoolkit.ogc.xml.FilterMarshallerPool;
import org.geotoolkit.ogc.xml.v110.FilterType;
import org.geotoolkit.ogc.xml.v110.LiteralType;
import org.geotoolkit.ogc.xml.v110.LowerBoundaryType;
import org.geotoolkit.ogc.xml.v110.ObjectFactory;
import org.geotoolkit.ogc.xml.v110.PropertyIsBetweenType;
import org.geotoolkit.ogc.xml.v110.PropertyIsEqualToType;
import org.geotoolkit.ogc.xml.v110.PropertyNameType;
import org.geotoolkit.ogc.xml.v110.UpperBoundaryType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Arrays;
import javax.xml.namespace.QName;
import static org.constellation.api.CommonConstants.QUERY_CONSTRAINT;
import org.constellation.filter.ElasticSearchFilterParser;
import org.constellation.filter.FilterParser;
import org.constellation.filter.FilterParserException;
import org.constellation.filter.FilterParserUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import static org.geotoolkit.ows.xml.OWSExceptionCode.INVALID_PARAMETER_VALUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A suite of test verifying the transformation of an XML filter into a Elasticsearch Query/filter
 *
 * @author Guilhem Legal
 */
public class ElasticSearchFilterParserTest {

    private FilterParser filterParser;
    private static MarshallerPool pool;
    private static final QName METADATA_QNAME = new QName("http://www.isotc211.org/2005/gmd", "MD_Metadata");

    @BeforeClass
    public static void setUpClass() throws Exception {
        pool = FilterMarshallerPool.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        filterParser = new ElasticSearchFilterParser();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void dummyTestFilterTest() throws Exception {
        System.out.println(QueryBuilders.andQuery(QueryBuilders.termQuery("test", "ss"), QueryBuilders.termQuery("test", "ss")).toString());
        System.out.println(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("test", "ss")).must(QueryBuilders.termQuery("test", "ss")).toString());
    }
    /**
     * Test simple comparison filter.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void simpleComparisonFilterTest() throws Exception {

        Unmarshaller filterUnmarshaller = pool.acquireUnmarshaller();

        /**
         * Test 1: a simple Filter propertyIsLike
         */
        String XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"                                                               +
			   "    <ogc:PropertyIsLike escapeChar=\"\\\" singleChar=\"?\" wildCard=\"*\">" +
                           "        <ogc:PropertyName>apiso:Title</ogc:PropertyName>"                   +
			   "        <ogc:Literal>*VM*</ogc:Literal>"                                    +
			   "    </ogc:PropertyIsLike>"                                                  +
                           "</ogc:Filter>";
        StringReader reader = new StringReader(XMLrequest);

        JAXBElement element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        FilterType filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        SpatialQuery spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        XContentBuilder result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"wildcard\":{\"Title_sort\":\"*VM*\"}}");


        /**
         * Test 2: a simple Filter PropertyIsEqualTo
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"    +
	            "    <ogc:PropertyIsEqualTo>"                              +
                    "        <ogc:PropertyName>apiso:Title</ogc:PropertyName>" +
                    "        <ogc:Literal>VM</ogc:Literal>"                    +
		    "    </ogc:PropertyIsEqualTo>"                             +
                    "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"term\":{\"Title\":\"VM\"}}");

        /**
         * Test 3: a simple Filter PropertyIsNotEqualTo
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"    +
	            "    <ogc:PropertyIsNotEqualTo>"                           +
                    "        <ogc:PropertyName>apiso:Title</ogc:PropertyName>" +
                    "        <ogc:Literal>VM</ogc:Literal>"                    +
		    "    </ogc:PropertyIsNotEqualTo>"                          +
                    "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"not\":{\"term\":{\"Title\":\"VM\"}}}");


        /**
         * Test 4: a simple Filter PropertyIsNull
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"    +
	            "    <ogc:PropertyIsNull>"                           +
                    "        <ogc:PropertyName>apiso:Title</ogc:PropertyName>" +
                    "    </ogc:PropertyIsNull>"                          +
                    "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"missing\":{\"field\":\"Title\"}}");

        /**
         * Test 5: a simple Filter PropertyIsGreaterThanOrEqualTo
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"           +
	            "    <ogc:PropertyIsGreaterThanOrEqualTo>"                        +
                    "        <ogc:PropertyName>apiso:CreationDate</ogc:PropertyName>" +
                    "        <ogc:Literal>2007-06-02</ogc:Literal>"                   +
                    "    </ogc:PropertyIsGreaterThanOrEqualTo>"                       +
                    "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"range\":{\"CreationDate\":{\"gte\":\"2007-06-02\"}}}");

        /**
         * Test 6: a simple Filter PropertyIsGreaterThan
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"           +
	            "    <ogc:PropertyIsGreaterThan>"                                 +
                    "        <ogc:PropertyName>apiso:CreationDate</ogc:PropertyName>" +
                    "        <ogc:Literal>2007-06-02</ogc:Literal>"                   +
                    "    </ogc:PropertyIsGreaterThan>"                                +
                    "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"range\":{\"CreationDate\":{\"gt\":\"2007-06-02\"}}}");

        /**
         * Test 7: a simple Filter PropertyIsLessThan
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"           +
	            "    <ogc:PropertyIsLessThan>"                                 +
                    "        <ogc:PropertyName>apiso:CreationDate</ogc:PropertyName>" +
                    "        <ogc:Literal>2007-06-02</ogc:Literal>"                   +
                    "    </ogc:PropertyIsLessThan>"                                +
                    "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"range\":{\"CreationDate\":{\"lt\":\"2007-06-02\"}}}");


         /**
         * Test 8: a simple Filter PropertyIsLessThanOrEqualTo
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"           +
	            "    <ogc:PropertyIsLessThanOrEqualTo>"                                 +
                    "        <ogc:PropertyName>apiso:CreationDate</ogc:PropertyName>" +
                    "        <ogc:Literal>2007-06-02</ogc:Literal>"                   +
                    "    </ogc:PropertyIsLessThanOrEqualTo>"                                +
                    "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"range\":{\"CreationDate\":{\"lte\":\"2007-06-02\"}}}");


        /**
         * Test 9: a simple Filter PropertyIsBetween
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">" +
	            "    <ogc:PropertyIsBetween>"                                     +
                    "        <ogc:PropertyName>apiso:CreationDate</ogc:PropertyName>" +
                    "        <ogc:LowerBoundary>"                                     +
                    "           <ogc:Literal>2007-06-02</ogc:Literal>"                +
                    "        </ogc:LowerBoundary>"                                    +
                    "        <ogc:UpperBoundary>"                                     +
                    "           <ogc:Literal>2007-06-04</ogc:Literal>"                +
                    "       </ogc:UpperBoundary>"                                     +
                    "    </ogc:PropertyIsBetween>"                                    +
                    "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"range\":{\"CreationDate\":{\"gte\":\"2007-06-02\",\"lte\":\"2007-06-04\"}}}");

         /**
         * Test 10: a simple empty Filter
         */
        QueryConstraintType nullConstraint = null;
        spaQuery = (SpatialQuery) filterParser.getQuery(nullConstraint, null, null, null);

        assertTrue(spaQuery.getSpatialFilter() == null);

        assertEquals(spaQuery.getQuery(), "metafile:doc");

        /**
         * Test 11: a simple Filter PropertyIsLessThanOrEqualTo with numeric field
         */
        filter = FilterParserUtils.cqlToFilter("CloudCover <= 12");

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"range\":{\"CloudCover\":{\"lte\":12}}}");

        /**
         * Test 11: a simple Filter PropertyIsGreaterThan with numeric field
         */
        filter = FilterParserUtils.cqlToFilter("CloudCover > 12");

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"range\":{\"CloudCover\":{\"gt\":12}}}");

        /**
         * Test 12: a simple Filter PropertyIsGreaterThan with numeric field + typeName
         */
        filter = FilterParserUtils.cqlToFilter("CloudCover > 12");

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, Arrays.asList(METADATA_QNAME));

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"and\":[{\"range\":{\"CloudCover\":{\"gt\":12}}},{\"term\":{\"objectType_sort\":\"MD_Metadata\"}}]}");

        pool.recycle(filterUnmarshaller);
    }

    @Test
    public void comparisonFilterOnDateTest() throws Exception {
        Unmarshaller filterUnmarshaller = pool.acquireUnmarshaller();

        /**
         * Test 1: a simple Filter PropertyIsEqualTo on a Date field
         */
        String XMLrequest =
                    "<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"           +
	            "    <ogc:PropertyIsEqualTo>"                                 +
                    "        <ogc:PropertyName>apiso:CreationDate</ogc:PropertyName>" +
                    "        <ogc:Literal>2007-06-02</ogc:Literal>"                   +
                    "    </ogc:PropertyIsEqualTo>"                                +
                    "</ogc:Filter>";

        StringReader reader = new StringReader(XMLrequest);

        JAXBElement element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        FilterType filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        SpatialQuery spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        XContentBuilder result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"term\":{\"CreationDate\":\"2007-06-02\"}}");

        /**
         * Test 2: a simple Filter PropertyIsLike on a Date field
         */
        XMLrequest =
                    "<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"           +
	            "    <ogc:PropertyIsLike escapeChar=\"\\\" singleChar=\"?\" wildCard=\"*\">" +
                    "        <ogc:PropertyName>apiso:CreationDate</ogc:PropertyName>"                   +
		    "        <ogc:Literal>200*-06-02</ogc:Literal>"                                    +
		    "    </ogc:PropertyIsLike>"                                                  +
                    "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"wildcard\":{\"CreationDate_sort\":\"200*0602\"}}");

        /**
         * Test 3: a simple Filter PropertyIsLike on a identifier field
         */
        XMLrequest =
                    "<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"           +
	            "    <ogc:PropertyIsLike escapeChar=\"\\\" singleChar=\"?\" wildCard=\"*\">" +
                    "        <ogc:PropertyName>identifier</ogc:PropertyName>"                   +
		    "        <ogc:Literal>*chain_acq_1*</ogc:Literal>"                                    +
		    "    </ogc:PropertyIsLike>"                                                  +
                    "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"wildcard\":{\"identifier_sort\":\"*chain_acq_1*\"}}");

        /**
         * Test 4: a simple Filter PropertyIsLike on a identifier field + typeName
         */
        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, Arrays.asList(METADATA_QNAME));

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"and\":[{\"wildcard\":{\"identifier_sort\":\"*chain_acq_1*\"}},{\"term\":{\"objectType_sort\":\"MD_Metadata\"}}]}");

        pool.recycle(filterUnmarshaller);
    }

    /**
     * Test simple logical filter (unary and binary).
     *
     * @throws java.lang.Exception
     */
    @Test
    public void FiltertoCQLTest() throws Exception {

        ObjectFactory factory = new ObjectFactory();
        final PropertyNameType propertyName = new PropertyNameType("ATTR1");
        final LowerBoundaryType low = new LowerBoundaryType();
        final LiteralType lowLit = new LiteralType("10");
        low.setExpression(factory.createLiteral(lowLit));
        final UpperBoundaryType upp = new UpperBoundaryType();
        final LiteralType uppLit = new LiteralType("20");
        upp.setExpression(factory.createLiteral(uppLit));
        final PropertyIsBetweenType pib = new PropertyIsBetweenType(factory.createPropertyName(propertyName), low, upp);
        FilterType filter = new FilterType(pib);
        String result = FilterParserUtils.filterToCql(filter);
        String expResult = "";
        assertEquals(expResult, result);

        final LiteralType lit = new LiteralType("10");
        final PropertyIsEqualToType pe = new PropertyIsEqualToType(lit, propertyName, Boolean.TRUE);
        filter = new FilterType(pe);
        result = FilterParserUtils.filterToCql(filter);
        expResult = "";
        assertEquals(expResult, result);
    }

    /**
     * Test simple logical filter (unary and binary).
     *
     * @throws java.lang.Exception
     */
    @Test
    public void simpleLogicalFilterTest() throws Exception {

        Unmarshaller filterUnmarshaller = pool.acquireUnmarshaller();
        /**
         * Test 1: a simple Filter AND between two propertyIsEqualTo
         */
        String XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"         +
                           "    <ogc:And>                                        "         +
			   "        <ogc:PropertyIsEqualTo>"                               +
                           "            <ogc:PropertyName>apiso:Title</ogc:PropertyName>"  +
                           "            <ogc:Literal>starship trooper</ogc:Literal>"       +
		           "        </ogc:PropertyIsEqualTo>"                              +
                           "        <ogc:PropertyIsEqualTo>"                               +
                           "            <ogc:PropertyName>apiso:Author</ogc:PropertyName>" +
                           "            <ogc:Literal>Timothee Gustave</ogc:Literal>"       +
		           "        </ogc:PropertyIsEqualTo>"                              +
                           "    </ogc:And>"                                                +
                           "</ogc:Filter>";
        StringReader reader = new StringReader(XMLrequest);

        JAXBElement element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        FilterType filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        SpatialQuery spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        XContentBuilder result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"and\":[{\"term\":{\"Title\":\"starship trooper\"}},{\"term\":{\"Author\":\"Timothee Gustave\"}}]}");

        /**
         * Test 2: a simple Filter OR between two propertyIsEqualTo
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"                +
                           "    <ogc:Or>                                        "         +
			   "        <ogc:PropertyIsEqualTo>"                               +
                           "            <ogc:PropertyName>apiso:Title</ogc:PropertyName>"  +
                           "            <ogc:Literal>starship trooper</ogc:Literal>"       +
		           "        </ogc:PropertyIsEqualTo>"                              +
                           "        <ogc:PropertyIsEqualTo>"                               +
                           "            <ogc:PropertyName>apiso:Author</ogc:PropertyName>" +
                           "            <ogc:Literal>Timothee Gustave</ogc:Literal>"       +
		           "        </ogc:PropertyIsEqualTo>"                              +
                           "    </ogc:Or>"                                                +
                           "</ogc:Filter>";
        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"or\":[{\"term\":{\"Title\":\"starship trooper\"}},{\"term\":{\"Author\":\"Timothee Gustave\"}}]}");


        /**
         * Test 3: a simple Filter OR between three propertyIsEqualTo
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"                +
                           "    <ogc:Or>                                        "          +
			   "        <ogc:PropertyIsEqualTo>"                               +
                           "            <ogc:PropertyName>apiso:Title</ogc:PropertyName>"  +
                           "            <ogc:Literal>starship trooper</ogc:Literal>"       +
		           "        </ogc:PropertyIsEqualTo>"                              +
                           "        <ogc:PropertyIsEqualTo>"                               +
                           "            <ogc:PropertyName>apiso:Author</ogc:PropertyName>" +
                           "            <ogc:Literal>Timothee Gustave</ogc:Literal>"       +
		           "        </ogc:PropertyIsEqualTo>"                              +
                           "        <ogc:PropertyIsEqualTo>"                               +
                           "            <ogc:PropertyName>apiso:Id</ogc:PropertyName>"     +
                           "            <ogc:Literal>268</ogc:Literal>"                    +
		           "        </ogc:PropertyIsEqualTo>"                              +
                           "    </ogc:Or> "                                                +
                           "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"or\":[{\"term\":{\"Title\":\"starship trooper\"}},{\"term\":{\"Author\":\"Timothee Gustave\"}},{\"term\":{\"Id\":\"268\"}}]}");


        /**
         * Test 4: a simple Filter Not propertyIsEqualTo
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"                 +
                           "    <ogc:Not>                                        "          +
			   "        <ogc:PropertyIsEqualTo>"                                +
                           "            <ogc:PropertyName>apiso:Title</ogc:PropertyName>"   +
                           "            <ogc:Literal>starship trooper</ogc:Literal>"        +
		           "        </ogc:PropertyIsEqualTo>"                               +
                           "    </ogc:Not>"                                                 +
                           "</ogc:Filter>";
        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"not\":{\"term\":{\"Title\":\"starship trooper\"}}}");

        /**
         * Test 5: a simple Filter Not propertyIsNotEqualTo
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"                 +
                           "    <ogc:Not>                                        "          +
			   "        <ogc:PropertyIsNotEqualTo>"                                +
                           "            <ogc:PropertyName>apiso:Title</ogc:PropertyName>"   +
                           "            <ogc:Literal>starship trooper</ogc:Literal>"        +
		           "        </ogc:PropertyIsNotEqualTo>"                               +
                           "    </ogc:Not>"                                                 +
                           "</ogc:Filter>";
        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"not\":{\"not\":{\"term\":{\"Title\":\"starship trooper\"}}}}");

        /**
         * Test 6: a simple Filter Not PropertyIsGreaterThanOrEqualTo
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"                 +
                           "    <ogc:Not>                                        "          +
			   "    <ogc:PropertyIsGreaterThanOrEqualTo>"                        +
                           "        <ogc:PropertyName>apiso:CreationDate</ogc:PropertyName>" +
                           "        <ogc:Literal>2007-06-02</ogc:Literal>"                   +
                           "    </ogc:PropertyIsGreaterThanOrEqualTo>"                       +
                           "    </ogc:Not>"                                                 +
                           "</ogc:Filter>";
        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"not\":{\"range\":{\"CreationDate\":{\"gte\":\"2007-06-02\"}}}}");

        /**
         * Test 7: a simple Filter Not PropertyIsGreaterThanOrEqualTo + typeName
         */
        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, Arrays.asList(METADATA_QNAME));

        assertTrue(spaQuery.getSpatialFilter()  instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"and\":[{\"term\":{\"objectType_sort\":\"MD_Metadata\"}},{\"not\":{\"range\":{\"CreationDate\":{\"gte\":\"2007-06-02\"}}}}]}");

        pool.recycle(filterUnmarshaller);
    }


    /**
     * Test simple Spatial filter
     *
     * @throws java.lang.Exception
     */
    @Test
    public void simpleSpatialFilterTest() throws Exception {

        Unmarshaller filterUnmarshaller = pool.acquireUnmarshaller();

        /**
         * Test 1: a simple spatial Filter Intersects
         */
        String XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"          "  +
                           "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                           "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">         "  +
                           "    <ogc:Intersects>                                          "  +
                           "       <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName> "  +
                           "         <gml:Envelope srsName=\"EPSG:4326\">                 "  +
			   "             <gml:lowerCorner>7 12</gml:lowerCorner>          "  +
                           "             <gml:upperCorner>20 20</gml:upperCorner>         "  +
			   "        </gml:Envelope>                                       "  +
			   "    </ogc:Intersects>                                         "  +
                           "</ogc:Filter>";
        StringReader reader = new StringReader(XMLrequest);

        JAXBElement element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        FilterType filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    != null);

        SpatialQuery spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        XContentBuilder result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"ogc_filter\":{\"geoextent\":{\"minx\":7.0,\"maxx\":20.0,\"miny\":12.0,\"maxy\":20.0,\"CRS\":\"EPSG:4326\",\"filter\":\"INTERSECTS\"}}}");

        /**
         * Test 2: a simple Distance Filter DWithin
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"          " +
                    "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                    "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">         " +
                    "    <ogc:DWithin>                                             " +
                    "      <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>  " +
                    "        <gml:Point srsName=\"EPSG:4326\">                     " +
                    "           <gml:coordinates>3.4,2.5</gml:coordinates>         " +
                    "        </gml:Point>                                          " +
                    "        <ogc:Distance units='m'>1000</ogc:Distance>           " +
                    "    </ogc:DWithin>                                            " +
                    "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    != null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"ogc_filter\":{\"geoextent\":{\"x\":3.4,\"y\":2.5,\"distance\":1000.0,\"distance_unit\":\"m\",\"filter\":\"DWITHIN\"}}}");

        /**
         * Test 3: a simple spatial Filter Intersects
         */
        XMLrequest =       "<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"          "  +
                           "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                           "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">         "  +
                           "    <ogc:Intersects>                                          "  +
                           "       <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName> "  +
                           "           <gml:LineString srsName=\"EPSG:4326\">             "  +
                           "                <gml:coordinates ts=\" \" decimal=\".\" cs=\",\">1,2 10,15</gml:coordinates>" +
                           "           </gml:LineString>                                  "  +
			   "    </ogc:Intersects>                                         "  +
                           "</ogc:Filter>";
        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    != null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"ogc_filter\":{\"geoextent\":{\"linestring\":\"[1.0,2.0,10.0,15.0]\",\"filter\":\"INTERSECTS\"}}}");

        pool.recycle(filterUnmarshaller);
    }

    /**
     * Test invalid Filter
     *
     * @throws java.lang.Exception
     */
    @Test
    public void errorFilterTest() throws Exception {

        Unmarshaller filterUnmarshaller = pool.acquireUnmarshaller();

        /**
         * Test 1: a simple Filter PropertyIsGreaterThanOrEqualTo with bad time format
         */
        String XMLrequest =
                    "<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"           +
	            "    <ogc:PropertyIsGreaterThanOrEqualTo>"                        +
                    "        <ogc:PropertyName>apiso:CreationDate</ogc:PropertyName>" +
                    "        <ogc:Literal>bonjour</ogc:Literal>"                   +
                    "    </ogc:PropertyIsGreaterThanOrEqualTo>"                       +
                    "</ogc:Filter>";

        StringReader reader = new StringReader(XMLrequest);

        JAXBElement element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        FilterType filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        SpatialQuery spaQuery;
        boolean error = false;
        try {
            spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);
        } catch (FilterParserException ex) {
            assertEquals(QUERY_CONSTRAINT, ex.getLocator());
            assertEquals(INVALID_PARAMETER_VALUE, ex.getExceptionCode());
            error = true;
        }

       // assertTrue(error); TODO

        /**
         * Test 2: a simple Filter propertyIsLike without literal
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"                                                               +
			   "    <ogc:PropertyIsLike escapeChar=\"\\\" singleChar=\"?\" wildCard=\"*\">" +
                           "        <ogc:PropertyName>apiso:Title</ogc:PropertyName>"                   +
			   "    </ogc:PropertyIsLike>"                                                  +
                           "</ogc:Filter>";
        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        error = false;
        try {
            spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);
        } catch (FilterParserException ex) {
            assertEquals(QUERY_CONSTRAINT, ex.getLocator());
            assertEquals(INVALID_PARAMETER_VALUE, ex.getExceptionCode());
            error = true;
        }
        assertTrue(error);

        /**
         * Test 3: a simple Filter PropertyIsNull without propertyName
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"    +
	            "    <ogc:PropertyIsNull>"                           +
                    "    </ogc:PropertyIsNull>"                          +
                    "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        error = false;
        try {
            spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);
        } catch (FilterParserException ex) {
            assertEquals(QUERY_CONSTRAINT, ex.getLocator());
            assertEquals(INVALID_PARAMETER_VALUE, ex.getExceptionCode());
            error = true;
        }
        assertTrue(error);

        /**
         * Test 4: a simple Filter PropertyIsBetween without upper boundary
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">" +
	            "    <ogc:PropertyIsBetween>"                                     +
                    "        <ogc:PropertyName>apiso:CreationDate</ogc:PropertyName>" +
                    "        <ogc:LowerBoundary>"                                     +
                    "           <ogc:Literal>2007-06-02</ogc:Literal>"                +
                    "        </ogc:LowerBoundary>"                                    +
                    "    </ogc:PropertyIsBetween>"                                    +
                    "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        error = false;
        try {
            spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);
        } catch (FilterParserException ex) {
            assertEquals(QUERY_CONSTRAINT, ex.getLocator());
            assertEquals(INVALID_PARAMETER_VALUE, ex.getExceptionCode());
            error = true;
        }
        assertTrue(error);

         /**
         * Test 5: a simple Filter PropertyIsBetween without lower boundary
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">" +
	            "    <ogc:PropertyIsBetween>"                                     +
                    "        <ogc:PropertyName>apiso:CreationDate</ogc:PropertyName>" +
                    "        <ogc:UpperBoundary>"                                     +
                    "           <ogc:Literal>2007-06-02</ogc:Literal>"                +
                    "        </ogc:UpperBoundary>"                                    +
                    "    </ogc:PropertyIsBetween>"                                    +
                    "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        error = false;
        try {
            spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);
        } catch (FilterParserException ex) {
            assertEquals(QUERY_CONSTRAINT, ex.getLocator());
            assertEquals(INVALID_PARAMETER_VALUE, ex.getExceptionCode());
            error = true;
        }
        assertTrue(error);

         /**
         * Test 6: a simple Filter PropertyIsLessThanOrEqualTo without propertyName
         */
        XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"           +
	            "    <ogc:PropertyIsLessThanOrEqualTo>"                                 +
                    "        <ogc:PropertyName>apiso:CreationDate</ogc:PropertyName>" +
                    "    </ogc:PropertyIsLessThanOrEqualTo>"                                +
                    "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() != null);
        assertTrue(filter.getLogicOps()      == null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        error = false;
        try {
            spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);
        } catch (FilterParserException ex) {
            assertEquals(QUERY_CONSTRAINT, ex.getLocator());
            assertEquals(INVALID_PARAMETER_VALUE, ex.getExceptionCode());
            error = true;
        }
        assertTrue(error);

        pool.recycle(filterUnmarshaller);
    }

    /**
     * Test Multiple Spatial Filter
     *
     * @throws java.lang.Exception
     */
    @Test
    public void multipleSpatialFilterTest() throws Exception {

        Unmarshaller filterUnmarshaller = pool.acquireUnmarshaller();
        /**
         * Test 1: two spatial Filter with AND
         */
        String XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"                "  +
                           "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                           "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">"  +
                           "    <ogc:And>                                                       "  +
                           "        <ogc:Intersects>                                            "  +
                           "             <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName> "  +
                           "             <gml:Envelope srsName=\"EPSG:4326\">                   "  +
			   "                 <gml:lowerCorner>7 12</gml:lowerCorner>            "  +
                           "                 <gml:upperCorner>20 20</gml:upperCorner>           "  +
			   "             </gml:Envelope>                                        "  +
			   "        </ogc:Intersects>                                           "  +
                           "        <ogc:Intersects>                                            "  +
                           "           <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>   "  +
                           "             <gml:Envelope srsName=\"EPSG:4326\">                   "  +
			   "                  <gml:lowerCorner>-2 -4</gml:lowerCorner>          "  +
                           "                  <gml:upperCorner>12 12</gml:upperCorner>          "  +
			   "             </gml:Envelope>                                        "  +
			   "        </ogc:Intersects>                                           "  +
                           "    </ogc:And>                                                      "  +
                           "</ogc:Filter>";

        StringReader reader = new StringReader(XMLrequest);

        JAXBElement element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        FilterType filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        SpatialQuery spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        XContentBuilder result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"and\":[{\"ogc_filter\":{\"geoextent\":{\"minx\":7.0,\"maxx\":20.0,\"miny\":12.0,\"maxy\":20.0,\"CRS\":\"EPSG:4326\",\"filter\":\"INTERSECTS\"}}},{\"ogc_filter\":{\"geoextent\":{\"minx\":-2.0,\"maxx\":12.0,\"miny\":-4.0,\"maxy\":12.0,\"CRS\":\"EPSG:4326\",\"filter\":\"INTERSECTS\"}}}]}");


        /**
         * Test 2: three spatial Filter with OR
         */
       XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"                "  +
                   "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                   "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">               "  +
                   "    <ogc:Or>                                                        "  +
                   "        <ogc:Intersects>                                            "  +
                   "             <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName> "  +
                   "             <gml:Envelope srsName=\"EPSG:4326\">                   "  +
                   "                 <gml:lowerCorner>7 12</gml:lowerCorner>            "  +
                   "                 <gml:upperCorner>20 20</gml:upperCorner>           "  +
		   "             </gml:Envelope>                                        "  +
                   "        </ogc:Intersects>                                           "  +
		   "        <ogc:Contains>                                              "  +
                   "             <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName> "  +
                   "             <gml:Point srsName=\"EPSG:4326\">                      "  +
                   "                 <gml:coordinates>3.4,2.5</gml:coordinates>         "  +
                   "            </gml:Point>                                            "  +
		   "        </ogc:Contains>                                             "  +
                   "         <ogc:BBOX>                                                 "  +
                   "              <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>"  +
		   "              <gml:Envelope srsName=\"EPSG:4326\">                  "  +
                   "                   <gml:lowerCorner>-20 -20</gml:lowerCorner>       "  +
		   "                   <gml:upperCorner>20 20</gml:upperCorner>         "  +
		   "              </gml:Envelope>                                       "  +
		   "       </ogc:BBOX>                                                  "  +
                   "    </ogc:Or>                                                       "  +
                   "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"or\":[{\"ogc_filter\":{\"geoextent\":{\"minx\":7.0,\"maxx\":20.0,\"miny\":12.0,\"maxy\":20.0,\"CRS\":\"EPSG:4326\",\"filter\":\"INTERSECTS\"}}},{\"ogc_filter\":{\"geoextent\":{\"x\":3.4,\"y\":2.5,\"filter\":\"CONTAINS\"}}},{\"ogc_filter\":{\"geoextent\":{\"filter\":\"BBOX\",\"minx\":-20.0,\"maxx\":20.0,\"miny\":-20.0,\"maxy\":20.0,\"CRS\":\"EPSG:4326\"}}}]}");


         /**
         * Test 3: three spatial Filter F1 AND (F2 OR F3)
         */
       XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"                   "  +
                   "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                   "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">                  "  +
                   "    <ogc:And>                                                          "  +
                   "        <ogc:Intersects>                                               "  +
                   "             <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>    "  +
                   "             <gml:Envelope srsName=\"EPSG:4326\">                      "  +
                   "                 <gml:lowerCorner>7 12</gml:lowerCorner>               "  +
                   "                 <gml:upperCorner>20 20</gml:upperCorner>              "  +
		   "             </gml:Envelope>                                           "  +
                   "        </ogc:Intersects>                                              "  +
                   "        <ogc:Or>                                                       "  +
		   "            <ogc:Contains>                                             "  +
                   "                <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName> "  +
                   "                <gml:Point srsName=\"EPSG:4326\">                      "  +
                   "                    <gml:coordinates>3.4,2.5</gml:coordinates>         "  +
                   "                </gml:Point>                                           "  +
		   "            </ogc:Contains>                                            "  +
                   "            <ogc:BBOX>                                                 "  +
                   "                <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName> "  +
		   "                <gml:Envelope srsName=\"EPSG:4326\">                   "  +
                   "                    <gml:lowerCorner>-20 -20</gml:lowerCorner>         "  +
		   "                    <gml:upperCorner>20 20</gml:upperCorner>           "  +
		   "                </gml:Envelope>                                        "  +
		   "            </ogc:BBOX>                                                "  +
                   "        </ogc:Or>                                                      "  +
                   "    </ogc:And>                                                         "  +
                   "</ogc:Filter>                                                          ";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"and\":[{\"or\":[{\"ogc_filter\":{\"geoextent\":{\"x\":3.4,\"y\":2.5,\"filter\":\"CONTAINS\"}}},{\"ogc_filter\":{\"geoextent\":{\"filter\":\"BBOX\",\"minx\":-20.0,\"maxx\":20.0,\"miny\":-20.0,\"maxy\":20.0,\"CRS\":\"EPSG:4326\"}}}]},{\"ogc_filter\":{\"geoextent\":{\"minx\":7.0,\"maxx\":20.0,\"miny\":12.0,\"maxy\":20.0,\"CRS\":\"EPSG:4326\",\"filter\":\"INTERSECTS\"}}}]}");

         /**
         * Test 4: three spatial Filter (NOT F1) AND F2 AND F3
         */
       XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"                   "  +
                   "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                   "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">  "  +
                   "    <ogc:And>                                                          "  +
                   "        <ogc:Not>                                                      "  +
                   "            <ogc:Intersects>                                           "  +
                   "                <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName> "  +
                   "                <gml:Envelope srsName=\"EPSG:4326\">                   "  +
                   "                    <gml:lowerCorner>7 12</gml:lowerCorner>            "  +
                   "                    <gml:upperCorner>20 20</gml:upperCorner>           "  +
		   "                </gml:Envelope>                                        "  +
                   "            </ogc:Intersects>                                          "  +
                   "        </ogc:Not>                                                     "  +
		   "        <ogc:Contains>                                                 "  +
                   "             <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>    "  +
                   "             <gml:Point srsName=\"EPSG:4326\">                         "  +
                   "                 <gml:coordinates>3.4,2.5</gml:coordinates>            "  +
                   "            </gml:Point>                                               "  +
		   "        </ogc:Contains>                                                "  +
                   "         <ogc:BBOX>                                                    "  +
                   "              <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>   "  +
		   "              <gml:Envelope srsName=\"EPSG:4326\">                     "  +
                   "                   <gml:lowerCorner>-20 -20</gml:lowerCorner>          "  +
		   "                   <gml:upperCorner>20 20</gml:upperCorner>            "  +
		   "              </gml:Envelope>                                          "  +
		   "       </ogc:BBOX>                                                     "  +
                   "    </ogc:And>                                                         "  +
                   "</ogc:Filter>                                                          ";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"and\":[{\"not\":{\"ogc_filter\":{\"geoextent\":{\"minx\":7.0,\"maxx\":20.0,\"miny\":12.0,\"maxy\":20.0,\"CRS\":\"EPSG:4326\",\"filter\":\"INTERSECTS\"}}}},{\"ogc_filter\":{\"geoextent\":{\"x\":3.4,\"y\":2.5,\"filter\":\"CONTAINS\"}}},{\"ogc_filter\":{\"geoextent\":{\"filter\":\"BBOX\",\"minx\":-20.0,\"maxx\":20.0,\"miny\":-20.0,\"maxy\":20.0,\"CRS\":\"EPSG:4326\"}}}]}");

        /**
         * Test 5: three spatial Filter NOT (F1 OR F2) AND F3
         */
       XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"                      "  +
                   "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                   "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">                     "  +
                   "    <ogc:And>                                                             "  +
                   "        <ogc:Not>                                                         "  +
                   "            <ogc:Or>                                                      "  +
                   "                <ogc:Intersects>                                          "  +
                   "                    <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>"  +
                   "                    <gml:Envelope srsName=\"EPSG:4326\">                  "  +
                   "                        <gml:lowerCorner>7 12</gml:lowerCorner>           "  +
                   "                        <gml:upperCorner>20 20</gml:upperCorner>          "  +
		   "                    </gml:Envelope>                                       "  +
                   "                </ogc:Intersects>                                         "  +
		   "                <ogc:Contains>                                            "  +
                   "                    <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>"  +
                   "                    <gml:Point srsName=\"EPSG:4326\">                     "  +
                   "                        <gml:coordinates>3.4,2.5</gml:coordinates>        "  +
                   "                    </gml:Point>                                          "  +
		   "                </ogc:Contains>                                           "  +
                   "           </ogc:Or>                                                      "  +
                   "        </ogc:Not>                                                        "  +
                   "         <ogc:BBOX>                                                       "  +
                   "              <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>      "  +
		   "              <gml:Envelope srsName=\"EPSG:4326\">                        "  +
                   "                   <gml:lowerCorner>-20 -20</gml:lowerCorner>             "  +
		   "                   <gml:upperCorner>20 20</gml:upperCorner>               "  +
		   "              </gml:Envelope>                                             "  +
		   "       </ogc:BBOX>                                                        "  +
                   "    </ogc:And>                                                            "  +
                   "</ogc:Filter>                                                             ";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"and\":[{\"not\":{\"or\":[{\"ogc_filter\":{\"geoextent\":{\"minx\":7.0,\"maxx\":20.0,\"miny\":12.0,\"maxy\":20.0,\"CRS\":\"EPSG:4326\",\"filter\":\"INTERSECTS\"}}},{\"ogc_filter\":{\"geoextent\":{\"x\":3.4,\"y\":2.5,\"filter\":\"CONTAINS\"}}}]}},{\"ogc_filter\":{\"geoextent\":{\"filter\":\"BBOX\",\"minx\":-20.0,\"maxx\":20.0,\"miny\":-20.0,\"maxy\":20.0,\"CRS\":\"EPSG:4326\"}}}]}");

        pool.recycle(filterUnmarshaller);
    }

    /**
     * Test complex query with both comparison, logical and spatial query
     *
     * @throws java.lang.Exception
     */
    @Test
    public void multipleMixedFilterTest() throws Exception {

        Unmarshaller filterUnmarshaller = pool.acquireUnmarshaller();

        /**
         * Test 1: PropertyIsLike AND INTERSECT
         */
        String XMLrequest ="<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"                          " +
                           "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                           "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">                         " +
                           "    <ogc:And>                                                                 " +
			   "        <ogc:PropertyIsLike escapeChar=\"\\\" singleChar=\"?\" wildCard=\"*\">" +
                           "           <ogc:PropertyName>apiso:Title</ogc:PropertyName>                   " +
			   "           <ogc:Literal>*VM*</ogc:Literal>                                    " +
			   "        </ogc:PropertyIsLike>                                                 " +
                           "        <ogc:Intersects>                                                      " +
                           "           <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>             " +
                           "             <gml:Envelope srsName=\"EPSG:4326\">                             " +
			   "                  <gml:lowerCorner>-2 -4</gml:lowerCorner>                    " +
                           "                  <gml:upperCorner>12 12</gml:upperCorner>                    " +
			   "             </gml:Envelope>                                                  " +
			   "        </ogc:Intersects>                                                     " +
                           "    </ogc:And>                                                                " +
                           "</ogc:Filter>";

        StringReader reader = new StringReader(XMLrequest);

        JAXBElement element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        FilterType filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        SpatialQuery spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        XContentBuilder result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"and\":[{\"wildcard\":{\"Title_sort\":\"*VM*\"}},{\"ogc_filter\":{\"geoextent\":{\"minx\":-2.0,\"maxx\":12.0,\"miny\":-4.0,\"maxy\":12.0,\"CRS\":\"EPSG:4326\",\"filter\":\"INTERSECTS\"}}}]}");


        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);

        //assertTrue(spaFilter.getOGCFilter() instanceof Intersects);

        /**
         * Test 2: PropertyIsLike AND INTERSECT AND propertyIsEquals
         */
        XMLrequest =       "<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"                          " +
                           "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                           "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">                         " +
                           "    <ogc:And>                                                                 " +
			   "        <ogc:PropertyIsLike escapeChar=\"\\\" singleChar=\"?\" wildCard=\"*\">" +
                           "           <ogc:PropertyName>apiso:Title</ogc:PropertyName>                   " +
			   "           <ogc:Literal>*VM*</ogc:Literal>                                    " +
			   "        </ogc:PropertyIsLike>                                                 " +
                           "        <ogc:Intersects>                                                      " +
                           "           <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>             " +
                           "             <gml:Envelope srsName=\"EPSG:4326\">                             " +
			   "                  <gml:lowerCorner>-2 -4</gml:lowerCorner>                    " +
                           "                  <gml:upperCorner>12 12</gml:upperCorner>                    " +
			   "             </gml:Envelope>                                                  " +
			   "        </ogc:Intersects>                                                     " +
                           "        <ogc:PropertyIsEqualTo>                                               " +
                           "            <ogc:PropertyName>apiso:Title</ogc:PropertyName>                  " +
                           "            <ogc:Literal>VM</ogc:Literal>                                     " +
                           "        </ogc:PropertyIsEqualTo>                                              " +
                           "    </ogc:And>                                                                " +
                           "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"and\":[{\"wildcard\":{\"Title_sort\":\"*VM*\"}},{\"term\":{\"Title\":\"VM\"}},{\"ogc_filter\":{\"geoextent\":{\"minx\":-2.0,\"maxx\":12.0,\"miny\":-4.0,\"maxy\":12.0,\"CRS\":\"EPSG:4326\",\"filter\":\"INTERSECTS\"}}}]}");

        /**
         * Test 3:  INTERSECT AND propertyIsEquals AND BBOX
         */
        XMLrequest =       "<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"                          " +
                           "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                           "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">                         " +
                           "    <ogc:And>                                                                 " +
                           "        <ogc:Intersects>                                                      " +
                           "           <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>             " +
                           "             <gml:Envelope srsName=\"EPSG:4326\">                             " +
			   "                  <gml:lowerCorner>-2 -4</gml:lowerCorner>                    " +
                           "                  <gml:upperCorner>12 12</gml:upperCorner>                    " +
			   "             </gml:Envelope>                                                  " +
			   "        </ogc:Intersects>                                                     " +
                           "        <ogc:PropertyIsEqualTo>                                               " +
                           "            <ogc:PropertyName>apiso:Title</ogc:PropertyName>                  " +
                           "            <ogc:Literal>VM</ogc:Literal>                                     " +
                           "        </ogc:PropertyIsEqualTo>                                              " +
                           "         <ogc:BBOX>                                                           " +
                           "              <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>          " +
                           "              <gml:Envelope srsName=\"EPSG:4326\">                            " +
                           "                   <gml:lowerCorner>-20 -20</gml:lowerCorner>                 " +
                           "                   <gml:upperCorner>20 20</gml:upperCorner>                   " +
                           "              </gml:Envelope>                                                 " +
                           "       </ogc:BBOX>                                                            " +
                           "    </ogc:And>                                                                " +
                           "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"and\":[{\"term\":{\"Title\":\"VM\"}},{\"ogc_filter\":{\"geoextent\":{\"minx\":-2.0,\"maxx\":12.0,\"miny\":-4.0,\"maxy\":12.0,\"CRS\":\"EPSG:4326\",\"filter\":\"INTERSECTS\"}}},{\"ogc_filter\":{\"geoextent\":{\"filter\":\"BBOX\",\"minx\":-20.0,\"maxx\":20.0,\"miny\":-20.0,\"maxy\":20.0,\"CRS\":\"EPSG:4326\"}}}]}");

        /**
         * Test 4: PropertyIsLike OR INTERSECT OR propertyIsEquals
         */
        XMLrequest =       "<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"                          " +
                           "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                           "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">                         " +
                           "    <ogc:Or>                                                                 " +
			   "        <ogc:PropertyIsLike escapeChar=\"\\\" singleChar=\"?\" wildCard=\"*\">" +
                           "           <ogc:PropertyName>apiso:Title</ogc:PropertyName>                   " +
			   "           <ogc:Literal>*VM*</ogc:Literal>                                    " +
			   "        </ogc:PropertyIsLike>                                                 " +
                           "        <ogc:Intersects>                                                      " +
                           "           <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>             " +
                           "             <gml:Envelope srsName=\"EPSG:4326\">                             " +
			   "                  <gml:lowerCorner>-2 -4</gml:lowerCorner>                    " +
                           "                  <gml:upperCorner>12 12</gml:upperCorner>                    " +
			   "             </gml:Envelope>                                                  " +
			   "        </ogc:Intersects>                                                     " +
                           "        <ogc:PropertyIsEqualTo>                                               " +
                           "            <ogc:PropertyName>apiso:Title</ogc:PropertyName>                  " +
                           "            <ogc:Literal>VM</ogc:Literal>                                     " +
                           "        </ogc:PropertyIsEqualTo>                                              " +
                           "    </ogc:Or>                                                                 " +
                           "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"or\":[{\"wildcard\":{\"Title_sort\":\"*VM*\"}},{\"term\":{\"Title\":\"VM\"}},{\"ogc_filter\":{\"geoextent\":{\"minx\":-2.0,\"maxx\":12.0,\"miny\":-4.0,\"maxy\":12.0,\"CRS\":\"EPSG:4326\",\"filter\":\"INTERSECTS\"}}}]}");


         /**
         * Test 5:  INTERSECT OR propertyIsEquals OR BBOX
         */
        XMLrequest =       "<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"                          " +
                           "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                           "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">                         " +
                           "    <ogc:Or>                                                                 " +
                           "        <ogc:Intersects>                                                      " +
                           "           <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>             " +
                           "             <gml:Envelope srsName=\"EPSG:4326\">                             " +
			   "                  <gml:lowerCorner>-2 -4</gml:lowerCorner>                    " +
                           "                  <gml:upperCorner>12 12</gml:upperCorner>                    " +
			   "             </gml:Envelope>                                                  " +
			   "        </ogc:Intersects>                                                     " +
                           "        <ogc:PropertyIsEqualTo>                                               " +
                           "            <ogc:PropertyName>apiso:Title</ogc:PropertyName>                  " +
                           "            <ogc:Literal>VM</ogc:Literal>                                     " +
                           "        </ogc:PropertyIsEqualTo>                                              " +
                           "         <ogc:BBOX>                                                           " +
                           "              <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>          " +
                           "              <gml:Envelope srsName=\"EPSG:4326\">                            " +
                           "                   <gml:lowerCorner>-20 -20</gml:lowerCorner>                 " +
                           "                   <gml:upperCorner>20 20</gml:upperCorner>                   " +
                           "              </gml:Envelope>                                                 " +
                           "       </ogc:BBOX>                                                            " +
                           "    </ogc:Or>                                                                " +
                           "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"or\":[{\"term\":{\"Title\":\"VM\"}},{\"ogc_filter\":{\"geoextent\":{\"minx\":-2.0,\"maxx\":12.0,\"miny\":-4.0,\"maxy\":12.0,\"CRS\":\"EPSG:4326\",\"filter\":\"INTERSECTS\"}}},{\"ogc_filter\":{\"geoextent\":{\"filter\":\"BBOX\",\"minx\":-20.0,\"maxx\":20.0,\"miny\":-20.0,\"maxy\":20.0,\"CRS\":\"EPSG:4326\"}}}]}");

        /**
         * Test 6:  INTERSECT AND (propertyIsEquals OR BBOX)
         */
        XMLrequest =       "<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"                          " +
                           "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                           "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">                         " +
                           "    <ogc:And>                                                                 " +
                           "        <ogc:Intersects>                                                      " +
                           "           <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>             " +
                           "             <gml:Envelope srsName=\"EPSG:4326\">                             " +
			   "                  <gml:lowerCorner>-2 -4</gml:lowerCorner>                    " +
                           "                  <gml:upperCorner>12 12</gml:upperCorner>                    " +
			   "             </gml:Envelope>                                                  " +
			   "        </ogc:Intersects>                                                     " +
                           "        <ogc:Or>                                                              " +
                           "            <ogc:PropertyIsEqualTo>                                           " +
                           "                <ogc:PropertyName>apiso:Title</ogc:PropertyName>              " +
                           "                <ogc:Literal>VM</ogc:Literal>                                 " +
                           "            </ogc:PropertyIsEqualTo>                                          " +
                           "            <ogc:BBOX>                                                        " +
                           "                <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>        " +
                           "                <gml:Envelope srsName=\"EPSG:4326\">                          " +
                           "                    <gml:lowerCorner>-20 -20</gml:lowerCorner>                " +
                           "                    <gml:upperCorner>20 20</gml:upperCorner>                  " +
                           "               </gml:Envelope>                                                " +
                           "            </ogc:BBOX>                                                       " +
                           "        </ogc:Or>                                                             " +
                           "    </ogc:And>                                                                " +
                           "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"and\":[{\"or\":[{\"term\":{\"Title\":\"VM\"}},{\"ogc_filter\":{\"geoextent\":{\"filter\":\"BBOX\",\"minx\":-20.0,\"maxx\":20.0,\"miny\":-20.0,\"maxy\":20.0,\"CRS\":\"EPSG:4326\"}}}]},{\"ogc_filter\":{\"geoextent\":{\"minx\":-2.0,\"maxx\":12.0,\"miny\":-4.0,\"maxy\":12.0,\"CRS\":\"EPSG:4326\",\"filter\":\"INTERSECTS\"}}}]}");


        /**
         * Test 7:  propertyIsNotEquals OR (propertyIsLike AND DWITHIN)
         */
        XMLrequest =       "<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"                                  " +
                           "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                           "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">                                 " +
                           "        <ogc:Or>                                                                      " +
                           "            <ogc:PropertyIsNotEqualTo>                                                " +
                           "                <ogc:PropertyName>apiso:Title</ogc:PropertyName>                      " +
                           "                <ogc:Literal>VMAI</ogc:Literal>                                       " +
                           "            </ogc:PropertyIsNotEqualTo>                                               " +
                           "            <ogc:And>                                                                 " +
                           "                <ogc:PropertyIsLike escapeChar=\"\\\" singleChar=\"?\" wildCard=\"*\">" +
                           "                    <ogc:PropertyName>apiso:Title</ogc:PropertyName>                  " +
			   "                    <ogc:Literal>LO?Li</ogc:Literal>                                  " +
			   "                </ogc:PropertyIsLike>                                                 " +
                           "                <ogc:DWithin>                                                         " +
                           "                    <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>            " +
                           "                    <gml:Point srsName=\"EPSG:4326\">                                 " +
                           "                        <gml:coordinates>3.4,2.5</gml:coordinates>                    " +
                           "                    </gml:Point>                                                      " +
                           "                    <ogc:Distance units='m'>1000</ogc:Distance>                       " +
                           "                </ogc:DWithin>                                                        " +
                           "            </ogc:And>                                                                " +
                           "        </ogc:Or>                                                                     " +
                           "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"or\":[{\"not\":{\"term\":{\"Title\":\"VMAI\"}}},{\"and\":[{\"wildcard\":{\"Title_sort\":\"LO?Li\"}},{\"ogc_filter\":{\"geoextent\":{\"x\":3.4,\"y\":2.5,\"distance\":1000.0,\"distance_unit\":\"m\",\"filter\":\"DWITHIN\"}}}]}]}");


        /**
         * Test 8:  propertyIsLike AND INTERSECT AND (propertyIsEquals OR BBOX) AND (propertyIsNotEquals OR (Beyond AND propertyIsLike))
         */
        XMLrequest =       "<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"                                  " +
                           "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                           "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">                                 " +
                           "    <ogc:And>                                                                         " +
                           "        <ogc:PropertyIsLike escapeChar=\"\\\" singleChar=\"?\" wildCard=\"*\">        " +
                           "           <ogc:PropertyName>apiso:Title</ogc:PropertyName>                           " +
			   "           <ogc:Literal>*VM*</ogc:Literal>                                            " +
			   "        </ogc:PropertyIsLike>                                                         " +
                           "        <ogc:Intersects>                                                              " +
                           "           <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>                     " +
                           "             <gml:Envelope srsName=\"EPSG:4326\">                                     " +
			   "                  <gml:lowerCorner>-2 -4</gml:lowerCorner>                            " +
                           "                  <gml:upperCorner>12 12</gml:upperCorner>                            " +
			   "             </gml:Envelope>                                                          " +
			   "        </ogc:Intersects>                                                             " +
                           "        <ogc:Or>                                                                      " +
                           "            <ogc:PropertyIsEqualTo>                                                   " +
                           "                <ogc:PropertyName>apiso:Title</ogc:PropertyName>                      " +
                           "                <ogc:Literal>PLOUF</ogc:Literal>                                      " +
                           "            </ogc:PropertyIsEqualTo>                                                  " +
                           "            <ogc:BBOX>                                                                " +
                           "                <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>                " +
                           "                <gml:Envelope srsName=\"EPSG:4326\">                                  " +
                           "                    <gml:lowerCorner>-20 -20</gml:lowerCorner>                        " +
                           "                    <gml:upperCorner>20 20</gml:upperCorner>                          " +
                           "               </gml:Envelope>                                                        " +
                           "            </ogc:BBOX>                                                               " +
                           "        </ogc:Or>                                                                     " +
                           "        <ogc:Or>                                                                      " +
                           "            <ogc:PropertyIsNotEqualTo>                                                " +
                           "                <ogc:PropertyName>apiso:Title</ogc:PropertyName>                      " +
                           "                <ogc:Literal>VMAI</ogc:Literal>                                       " +
                           "            </ogc:PropertyIsNotEqualTo>                                               " +
                           "            <ogc:And>                                                                 " +
                           "                <ogc:PropertyIsLike escapeChar=\"\\\" singleChar=\"?\" wildCard=\"*\">" +
                           "                    <ogc:PropertyName>apiso:Title</ogc:PropertyName>                  " +
			   "                    <ogc:Literal>LO?Li</ogc:Literal>                                  " +
			   "                </ogc:PropertyIsLike>                                                 " +
                           "                <ogc:DWithin>                                                         " +
                           "                    <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>            " +
                           "                    <gml:Point srsName=\"EPSG:4326\">                                 " +
                           "                        <gml:coordinates>3.4,2.5</gml:coordinates>                    " +
                           "                    </gml:Point>                                                      " +
                           "                    <ogc:Distance units='m'>1000</ogc:Distance>                       " +
                           "                </ogc:DWithin>                                                        " +
                           "            </ogc:And>                                                                " +
                           "        </ogc:Or>                                                                     " +
                           "    </ogc:And>                                                                        " +
                           "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"and\":[{\"wildcard\":{\"Title_sort\":\"*VM*\"}},{\"or\":[{\"term\":{\"Title\":\"PLOUF\"}},{\"ogc_filter\":{\"geoextent\":{\"filter\":\"BBOX\",\"minx\":-20.0,\"maxx\":20.0,\"miny\":-20.0,\"maxy\":20.0,\"CRS\":\"EPSG:4326\"}}}]},{\"or\":[{\"not\":{\"term\":{\"Title\":\"VMAI\"}}},{\"and\":[{\"wildcard\":{\"Title_sort\":\"LO?Li\"}},{\"ogc_filter\":{\"geoextent\":{\"x\":3.4,\"y\":2.5,\"distance\":1000.0,\"distance_unit\":\"m\",\"filter\":\"DWITHIN\"}}}]}]},{\"ogc_filter\":{\"geoextent\":{\"minx\":-2.0,\"maxx\":12.0,\"miny\":-4.0,\"maxy\":12.0,\"CRS\":\"EPSG:4326\",\"filter\":\"INTERSECTS\"}}}]}");


        /**
         * Test 9:  NOT propertyIsLike AND NOT INTERSECT AND NOT (propertyIsEquals OR BBOX) AND (propertyIsNotEquals OR (Beyond AND propertyIsLike))
         */
        XMLrequest =       "<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\"                                  " +
                           "            xmlns:gml=\"http://www.opengis.net/gml\"" +
                           "            xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\">                                 " +
                           "    <ogc:And>                                                                         " +
                           "        <ogc:Not>                                                                     " +
                           "            <ogc:PropertyIsLike escapeChar=\"\\\" singleChar=\"?\" wildCard=\"*\">    " +
                           "                <ogc:PropertyName>apiso:Title</ogc:PropertyName>                      " +
			   "                <ogc:Literal>*VM*</ogc:Literal>                                       " +
			   "            </ogc:PropertyIsLike>                                                     " +
                           "        </ogc:Not>                                                                    " +
                           "        <ogc:Not>                                                                     " +
                           "            <ogc:Intersects>                                                          " +
                           "                <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>                " +
                           "                <gml:Envelope srsName=\"EPSG:4326\">                                  " +
			   "                    <gml:lowerCorner>-2 -4</gml:lowerCorner>                          " +
                           "                    <gml:upperCorner>12 12</gml:upperCorner>                          " +
			   "                </gml:Envelope>                                                       " +
			   "            </ogc:Intersects>                                                         " +
                           "        </ogc:Not>                                                                    " +
                           "        <ogc:Not>                                                                     " +
                           "        <ogc:Or>                                                                      " +
                           "            <ogc:PropertyIsEqualTo>                                                   " +
                           "                <ogc:PropertyName>apiso:Title</ogc:PropertyName>                      " +
                           "                <ogc:Literal>PLOUF</ogc:Literal>                                      " +
                           "            </ogc:PropertyIsEqualTo>                                                  " +
                           "            <ogc:BBOX>                                                                " +
                           "                <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>                " +
                           "                <gml:Envelope srsName=\"EPSG:4326\">                                  " +
                           "                    <gml:lowerCorner>-20 -20</gml:lowerCorner>                        " +
                           "                    <gml:upperCorner>20 20</gml:upperCorner>                          " +
                           "               </gml:Envelope>                                                        " +
                           "            </ogc:BBOX>                                                               " +
                           "        </ogc:Or>                                                                     " +
                           "        </ogc:Not>                                                                     " +
                           "        <ogc:Or>                                                                      " +
                           "            <ogc:PropertyIsNotEqualTo>                                                " +
                           "                <ogc:PropertyName>apiso:Title</ogc:PropertyName>                      " +
                           "                <ogc:Literal>VMAI</ogc:Literal>                                       " +
                           "            </ogc:PropertyIsNotEqualTo>                                               " +
                           "            <ogc:And>                                                                 " +
                           "                <ogc:PropertyIsLike escapeChar=\"\\\" singleChar=\"?\" wildCard=\"*\">" +
                           "                    <ogc:PropertyName>apiso:Title</ogc:PropertyName>                  " +
			   "                    <ogc:Literal>LO?Li</ogc:Literal>                                  " +
			   "                </ogc:PropertyIsLike>                                                 " +
                           "                <ogc:DWithin>                                                         " +
                           "                    <ogc:PropertyName>apiso:BoundingBox</ogc:PropertyName>            " +
                           "                    <gml:Point srsName=\"EPSG:4326\">                                 " +
                           "                        <gml:coordinates>3.4,2.5</gml:coordinates>                    " +
                           "                    </gml:Point>                                                      " +
                           "                    <ogc:Distance units='m'>1000</ogc:Distance>                       " +
                           "                </ogc:DWithin>                                                        " +
                           "            </ogc:And>                                                                " +
                           "        </ogc:Or>                                                                     " +
                           "    </ogc:And>                                                                        " +
                           "</ogc:Filter>";

        reader = new StringReader(XMLrequest);

        element =  (JAXBElement) filterUnmarshaller.unmarshal(reader);
        filter = (FilterType) element.getValue();

        assertTrue(filter.getComparisonOps() == null);
        assertTrue(filter.getLogicOps()      != null);
        assertTrue(filter.getId().isEmpty()   );
        assertTrue(filter.getSpatialOps()    == null);

        spaQuery = (SpatialQuery) filterParser.getQuery(new QueryConstraintType(filter, "1.1.0"), null, null, null);

        assertTrue(spaQuery.getSpatialFilter() instanceof XContentBuilder);
        result = (XContentBuilder) spaQuery.getSpatialFilter();
        assertEquals(result.string(), "{\"and\":[{\"not\":{\"wildcard\":{\"Title_sort\":\"*VM*\"}}},{\"not\":{\"ogc_filter\":{\"geoextent\":{\"minx\":-2.0,\"maxx\":12.0,\"miny\":-4.0,\"maxy\":12.0,\"CRS\":\"EPSG:4326\",\"filter\":\"INTERSECTS\"}}}},{\"not\":{\"or\":[{\"term\":{\"Title\":\"PLOUF\"}},{\"ogc_filter\":{\"geoextent\":{\"filter\":\"BBOX\",\"minx\":-20.0,\"maxx\":20.0,\"miny\":-20.0,\"maxy\":20.0,\"CRS\":\"EPSG:4326\"}}}]}},{\"or\":[{\"not\":{\"term\":{\"Title\":\"VMAI\"}}},{\"and\":[{\"wildcard\":{\"Title_sort\":\"LO?Li\"}},{\"ogc_filter\":{\"geoextent\":{\"x\":3.4,\"y\":2.5,\"distance\":1000.0,\"distance_unit\":\"m\",\"filter\":\"DWITHIN\"}}}]}]}]}");

        pool.recycle(filterUnmarshaller);
    }


}
