package com.elearningbackend.utility;

import com.elearningbackend.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.util.Date;
import java.util.UUID;

/**
 * Created by dohalong on 05/12/2017.
 */
public abstract class SecurityUtil {
    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static String generateToken(UserDto userDto){
        // convert object UserDTO to retricted error null when get claims
        userDto = (UserDto) ServiceUtils.convertObject(userDto,"address","phone","avatar","displayName");
        return Jwts.builder()
               .setId(UUID.randomUUID().toString())
               .setSubject(userDto.getUsername())
               .claim("username",userDto.getUsername())
               .claim("role",userDto.getRole())
               .claim("email",userDto.getEmail())
               .claim("address",userDto.getAddress())
               .claim("phone",userDto.getPhone())
               .claim("avatar",userDto.getAvatar())
               .claim("display_name",userDto.getDisplayName())
               .setIssuedAt(new Date())
               .setExpiration(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
               .signWith(SignatureAlgorithm.HS512, Constants.SECRET)
               .compact();
    }

    public static void resetToken(HttpServletResponse response, UserDto userDto){
        response.setHeader(Constants.HEADER_AUTHORIZATION,Constants.TOKEN_PREFIX+ " " + SecurityUtil.generateToken(userDto));
    }
}
