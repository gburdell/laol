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
#include "laol/rt/map.hxx"


namespace laol {
    namespace rt {
        //static
        Laol::METHOD_BY_NAME
        Map::stMethodByName = {
            {"size", static_cast<TPMethod> (&Map::size)},
            {"empty?", static_cast<TPMethod> (&Map::empty_PRED)},
            {"insert!", static_cast<TPMethod> (&Map::insert_SELF)},
            {"merge", static_cast<TPMethod> (&Map::merge)},
            {"merge!", static_cast<TPMethod> (&Map::merge_SELF)},
            {"key?", static_cast<TPMethod> (&Map::key_PRED)},
            {"find", static_cast<TPMethod> (&Map::find)},
        };

        Map::Map(std::initializer_list<MAP::value_type> init)
        : m_map(init) {

        }

        LaolObj
        Map::toString(const LaolObj&, const LaolObj&) const {
            std::ostringstream oss;
            oss << "{";
            bool doComma = false;
            for (auto& ele : m_map) {
                if (doComma) {
                    oss << ", ";
                }
                oss << ele.first.toQString() << ": " << ele.second.toQString() ;
                doComma = true;
            }
            oss << "}";
            return new String(oss.str());
        }

        LaolObj
        Map::empty_PRED(const LaolObj& self, const LaolObj& args) const {
            return m_map.empty();
        }

        LaolObj
        Map::insert_SELF(const LaolObj& self, const LaolObj& args) const {
            return self;
        }

        LaolObj
        Map::merge(const LaolObj& self, const LaolObj& args) const {
            return self;
        }

        LaolObj
        Map::merge_SELF(const LaolObj& self, const LaolObj& args) const {
            return self;
        }

        LaolObj
        Map::key_PRED(const LaolObj& self, const LaolObj& args) const {
            return (m_map.end() != m_map.find(args));
        }

        LaolObj
        Map::find(const LaolObj& self, const LaolObj& args) const {
            auto it = m_map.find(args);
            return (it != m_map.end()) ? it->second : NULLOBJ;
        }

        Laol::TPMethod
        Map::getFunc(const string& methodNm) const {
            return Laol::getFunc(stMethodByName, methodNm);

        }

        Map::~Map() {
        }
    }
}
