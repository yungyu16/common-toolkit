package com.github.yungyu16.commom.toolkit.crypto;

import java.security.Key;
import java.security.spec.InvalidKeySpecException;

/**
 * CreatedDate: 2020/10/17
 * Author: songjialin
 */
public interface KeyParser {
    Key parse(byte[] keyData) throws InvalidKeySpecException;
}
