package com.mockuai.distributioncenter.core.service.action.relationship;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.qto.SellerRelationshipQTO;
import com.mockuai.distributioncenter.core.dao.SellerDAO;
import com.mockuai.distributioncenter.core.dao.SellerRelationshipDAO;
import com.mockuai.distributioncenter.core.domain.SellerDO;
import com.mockuai.distributioncenter.core.domain.SellerRelationshipDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by duke on 16/6/18.
 */
@Service
public class BuildRelationshipAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(BuildRelationshipAction.class);

    @Autowired
    private SellerRelationshipDAO sellerRelationshipDAO;

    @Autowired
    private SellerDAO sellerDAO;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        SellerRelationshipQTO relationshipQTO = new SellerRelationshipQTO();
        relationshipQTO.setStatus(1);
        List<SellerRelationshipDO> relationshipDOs = sellerRelationshipDAO.query(relationshipQTO);
        Map<Long, Node> nodeMap = new HashMap<Long, Node>();
        Map<Long, Long> relationshipMap = new HashMap<Long, Long>();
        LinkedList<Node> stack = new LinkedList<Node>();

        Node root = null;
        for (SellerRelationshipDO relationshipDO : relationshipDOs) {
            relationshipMap.put(relationshipDO.getUserId(), relationshipDO.getParentId());
            Node node = nodeMap.get(relationshipDO.getParentId());
            if (node == null) {
                node = new Node(relationshipDO.getParentId(), relationshipDO.getUserId());
                nodeMap.put(node.getUserId(), node);
            } else {
                node.addChild(relationshipDO.getUserId());
            }
            if (root == null && node.getUserId().equals(0L)) {
                root = node;
            }
        }

        // 进行拓扑排序，计算groupCount的值
        if (root != null) {
            stack.push(root);
        }

        while (!stack.isEmpty()) {
            Node node = stack.peek();
            Set<Long> childIds = node.getChildIds();
            if (childIds.isEmpty()) {
                // 如果回溯到根节点，则退出
                if (node.getUserId().equals(0L)) {
                    break;
                }
                // 如果节点的入度为0，则弹出栈，并更新它的父节点的groupCount
                // 更新该节点的groupCount值，加上它的directCount值
                node.increaseGroupCount(node.getDirectCount());
                Long parentId = relationshipMap.get(node.getUserId());
                Node parentNode = nodeMap.get(parentId);
                parentNode.increaseGroupCount(node.getGroupCount());
                stack.pop();
                continue;
            }
            for (Long childId : childIds) {
                Node n = nodeMap.get(childId);
                if (n != null) {
                    stack.push(n);
                }
            }
            node.getChildIds().clear();
        }
        // 去除根节点的父节点
        nodeMap.remove(0L);

        // 更新
        for (Map.Entry<Long, Node> entry : nodeMap.entrySet()) {
            Node node = entry.getValue();
            SellerDO sellerDO = sellerDAO.getByUserId(node.getUserId());
            sellerDO.setDirectCount(node.getDirectCount());
            sellerDO.setGroupCount(node.getGroupCount());
            sellerDAO.update(sellerDO);
        }

        return new DistributionResponse(true);
    }

    public static class Node {
        private Long userId;
        private Set<Long> childIds = new HashSet<Long>();
        private Long directCount;
        private Long groupCount;

        public Node(Long userId, Long childId) {
            this.userId = userId;
            this.childIds.add(childId);
            this.directCount = 1L;
            this.groupCount = 0L;
        }

        public Set<Long> getChildIds() {
            return childIds;
        }

        public void addChild(Long childId) {
            this.childIds.add(childId);
            this.directCount += 1L;
        }

        public void increaseGroupCount(Long count) {
            this.groupCount += count;
        }

        public Long getUserId() {
            return userId;
        }

        public Long getDirectCount() {
            return directCount;
        }

        public Long getGroupCount() {
            return groupCount;
        }
    }

    @Override
    public String getName() {
        return ActionEnum.BUILD_RELATION_SHIP.getActionName();
    }
}
