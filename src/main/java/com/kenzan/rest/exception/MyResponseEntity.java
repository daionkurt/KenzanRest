package com.kenzan.rest.exception;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

/**
 * Custom HttpEntity that handles the Company Profile's Entities responses
 * 
 * @author <a href="mailto:carlos.fz@ibm.com">Carlos Fernandez</a>
 *
 */
public class MyResponseEntity<T> extends HttpEntity<T>
{
    private final Object status;
    /**
     * Create a new {@code HttpEntity} with the given body, headers, and status code. Just used behind the nested
     * builder API.
     * 
     * @param body
     *     the entity body
     * @param headers
     *     the entity headers
     * @param status
     *     the status code (as {@code HttpStatus} or as {@code Integer} value)
     */
    public MyResponseEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers, Object status) {
        super(body, headers);
        Assert.notNull(status, "HttpStatus must not be null");
        this.status = status;
    }
    
    /**
     * Create a new {@code ResponseEntity} with the given body and status code, and no headers.
     * @param body the entity body
     * @param status the status code
     */
    public MyResponseEntity(@Nullable T body, HttpStatus status) {
            this(body, null, status);
        }
    /**
     * Return the HTTP status code of the response.
     * 
     * @return the HTTP status as an HttpStatus enum entry
     */
    public HttpStatus getStatusCode() {
        if (this.status instanceof HttpStatus) {
            return (HttpStatus) this.status;
        } else {
            return HttpStatus.valueOf((Integer) this.status);
        }
    }
    /**
     * Return the HTTP status code of the response.
     * 
     * @return the HTTP status as an int value
     * @since 4.3
     */
    public int getStatusCodeValue() {
        if (this.status instanceof HttpStatus) {
            return ((HttpStatus) this.status).value();
        } else {
            return (Integer) this.status;
        }
    }
    /**
     * Compares if the input <code>Object</code> is equals to this response entity
     * 
     */
    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!super.equals(other)) {
            return false;
        }
        MyResponseEntity<?> otherEntity = (MyResponseEntity<?>) other;
        return ObjectUtils.nullSafeEquals(this.status, otherEntity.status);
    }
    /**
     * Generates a hash code from this HTTP status
     */
    @Override
    public int hashCode() {
        return (super.hashCode() * 29 + ObjectUtils.nullSafeHashCode(this.status));
    }
    /**
     * Custom toString() function
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("<");
        builder.append(this.status.toString());
        if (this.status instanceof HttpStatus) {
            builder.append(' ');
            builder.append(((HttpStatus) this.status).getReasonPhrase());
        }
        builder.append(',');
        T body = getBody();
        HttpHeaders headers = getHeaders();
        if (body != null) {
            builder.append(body);
            builder.append(',');
        }
        builder.append(headers);
        builder.append('>');
        return builder.toString();
    }
}











