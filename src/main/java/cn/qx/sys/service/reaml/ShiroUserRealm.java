package cn.qx.sys.service.reaml;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qx.sys.entity.User;
import cn.qx.sys.mapper.SysUserMapper;

/**
 * Realm只是负责数据获取以及基本业务操作
 * 
 * @author Satone
 * @date 2019年2月20日下午9:19:59
 */
@Service
public class ShiroUserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserMapper sysUserDao;

    /**
     * 设置凭证匹配器
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        // 构建凭证匹配对象
        HashedCredentialsMatcher cMatcher = new HashedCredentialsMatcher();
        // 设置加密算法
        cMatcher.setHashAlgorithmName("MD5");
        // 设置加密次数
        cMatcher.setHashIterations(1);
        super.setCredentialsMatcher(cMatcher);
    }

    /**
     * 通过此方法完成认证数据的获取及封装,系统 底层会将认证数据传递认证管理器，由认证 管理器完成认证操作。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1.获取用户名(用户页面输入)
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        // 2.基于用户名查询用户信息
        User user = sysUserDao.findByName(username);
        // 3.判定用户是否存在
        if (user == null)
            throw new UnknownAccountException();
        // 4.判定用户是否已被禁用。
        if (user.getValid() == 0)
            throw new LockedAccountException();

        // 5.封装用户信息
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
        // 记住：构建什么对象要看方法的返回值
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, // principal (身份)
                user.getPassword(), // hashedCredentials
                credentialsSalt, // credentialsSalt
                getName());// realmName
        // 6.返回封装结果
        return info;// 返回值会传递给认证管理器(后续
        // 认证管理器会通过此信息完成认证操作)
    }

    /** 通过此方法完成授权信息的获取及封装 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        // 1.获取登录用户信息，例如用户id
//        SysUser user = (SysUser) principals.getPrimaryPrincipal();
//        Integer userId = user.getId();
//        // 2.基于用户id获取用户拥有的角色(sys_user_roles)
//        List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
//        if (roleIds == null || roleIds.size() == 0)
//            throw new AuthorizationException();
//        // 3.基于角色id获取菜单id(sys_role_menus)
//        Integer[] array = {};
//        List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(array));
//        if (menuIds == null || menuIds.size() == 0)
//            throw new AuthorizationException();
//        // 4.基于菜单id获取权限标识(sys_menus)
//        List<String> permissions = sysMenuDao.findPermissions(menuIds.toArray(array));
//        // 5.对权限标识信息进行封装并返回
//        Set<String> set = new HashSet<>();
//        for (String per : permissions) {
//            if (!StringUtils.isEmpty(per)) {
//                set.add(per);
//            }
//        }
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.setStringPermissions(set);
//        return info;// 返回给授权管理器
        return null;
    }

}