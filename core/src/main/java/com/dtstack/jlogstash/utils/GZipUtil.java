/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dtstack.jlogstash.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @company: www.dtstack.com
 * @Author ：Nanqi
 * @Date ：Created in 10:32 2019-07-24
 * @Description：Gip 压缩工具
 */
public class GZipUtil {

    private static final Logger LOG = LoggerFactory.getLogger(GZipUtil.class);


    public static byte[] compress(byte[] data) {
        ByteArrayOutputStream bos = null;
        GZIPOutputStream gzip = null;
        try {
            bos = new ByteArrayOutputStream(data.length);
            gzip = new GZIPOutputStream(bos);
            gzip.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e.getStackTrace());
                }
            }

            if (null != gzip) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e.getStackTrace());
                }
            }
        }
        byte[] compressed = bos.toByteArray();
        return compressed;
    }

    public static byte[] deCompress(byte[] compressed) {
        ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
        GZIPInputStream gis = null;
        byte[] backData = null;
        try {
            gis = new GZIPInputStream(bis);
            backData = IOUtils.toByteArray(gis);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e.getStackTrace());
        } finally {
            if (null != bis) {
                try {
                    bis.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e.getStackTrace());
                }
            }

            if (null != gis) {
                try {
                    gis.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e.getStackTrace());
                }
            }
        }
        return backData;
    }

    public static String compress(String rowData) {
        return new String(Base64Util.baseEncode(compress(rowData.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
    }

    public static String deCompress(String rowData) {
        return new String(deCompress(Base64Util.baseDecode(rowData.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
    }
}
