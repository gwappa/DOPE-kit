/*
 * MIT License
 *
 * Copyright (c) 2019 Keisuke Sehara
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package cc.chaos.util;

/**
 *  a class for representing success/failure result
 */
public class Result<V>
{
    boolean _success;
    String  _message;
    V       _object;

    /**
     *  set to private; create any object via success() or failure() instead.
     */
    private Result(boolean success, V object, String message){
        _success = success;
        _object  = object;
        _message = message;
    }

    public boolean isSuccessful() {
        return _success;
    }

    public boolean isFailure() {
        return !_success;
    }

    /**
     *  an alias of isFailure()
     */
    public boolean failed() {
        return !_success;
    }

    public String getMessage() {
        return _message;
    }

    public V get() {
        return _object;
    }

    /**
     *  creates a successful Result with an object.
     */
    public static <V> Result<V> success(V object) {
        return new Result<V>(true, object, "");
    }

    /**
     *  creates a failing Result from an object and a message.
     */
    public static <V> Result<V> failure(V object, String cause) {
        return new Result<V>(false, object, cause);
    }

    /**
     *  creates a failing Result from a message, with the content set to null.
     */
    public static <V> Result<V> failure(String cause) {
        return failure(null, cause);
    }
}
