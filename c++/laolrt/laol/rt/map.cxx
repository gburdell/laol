/*
 * The MIT License
 *
 * Copyright 2017 kpfalzer.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

#include <sstream>
#include "laol/rt/string.hxx"
#include "laol/rt/array.hxx"
#include "laol/rt/map.hxx"


namespace laol {
    namespace rt {
        //static
        Laol::METHOD_BY_NAME
        Map::stMethodByName = {
            {"size", static_cast<TPMethod> (&Map::size)},
            {"empty?", static_cast<TPMethod> (&Map::empty_PRED)},
            {"merge", static_cast<TPMethod> (&Map::merge)},
            {"merge!", static_cast<TPMethod> (&Map::merge_SELF)},
            {"key?", static_cast<TPMethod> (&Map::key_PRED)},
            {"find", static_cast<TPMethod> (&Map::find)},
            {"subscript_assign", static_cast<TPMethod> (&Map::subscript_assign)},
        };

        Laol::TPMethod
        Map::getFunc(const string& methodNm) const {
            return Laol::getFunc(stMethodByName, methodNm);

        }

        Map::Map(std::initializer_list<MAP::value_type> init)
        : m_map(init) {

        }

        Map::Map(const Map& from) {
            for (auto& ele : from.m_map) {
                m_map[ele.first] = ele.second;
            }
        }

        LaolObj 
        Map::subscript_assign(LaolObj& self, LaolObj& args) {
            ASSERT_TRUE(args.isA<Array>());
            Array& vargs = args.toType<Array>();
            const auto N = vargs.length(args, NULLOBJ).toLInt();
            if (2 != N) {
                throw ArityException(N, "2");
            }
            LaolObj key = vargs[0], val = vargs[1];
            m_map[key] = val;
            return self;
        }

        LaolObj
        Map::toString(LaolObj&, LaolObj&) {
            std::ostringstream oss;
            oss << "{";
            bool doComma = false;
            for (auto& ele : m_map) {
                if (doComma) {
                    oss << ", ";
                }
                oss << unconst(ele.first).toQString() << ": " << unconst(ele.second).toQString();
                doComma = true;
            }
            oss << "}";
            return new String(oss.str());
        }

        LaolObj
        Map::empty_PRED(LaolObj& self, LaolObj& args) {
            return m_map.empty();
        }

        LaolObj
        Map::merge_SELF(LaolObj& self, LaolObj& args) {
            Map& from = args.toType<Map>();
            for (auto& ele : from.m_map) {
                m_map[ele.first] = ele.second;
            }
            return self;
        }

        LaolObj
        Map::merge(LaolObj& self, LaolObj& args) {
            LaolObj copy = new Map(*this);
            return copy.toType<Map>().merge_SELF(copy, args);
        }

        LaolObj
        Map::key_PRED(LaolObj& self, LaolObj& args) {
            return (m_map.end() != m_map.find(args));
        }

        LaolObj
        Map::find(LaolObj& self, LaolObj& args) {
            auto it = m_map.find(args);
            return (it != m_map.end()) ? it->second : NULLOBJ;
        }

        Map::~Map() {
        }
    }
}
