package com.mockuai.seckillcenter.common.constant;

/**
 * Created by edgar.zr on 6/13/2016.
 */
public enum CacheKeyEnum {
	ITEM_KEY("item") {
		/**
		 * 商品详情
		 *
		 * bizCode_sellerId_itemId
		 *
		 *@param elem
		 * @return
		 */
		@Override
		public String getKey(Object... elem) {
			return new StringBuilder()
					       .append("item")
					       .append("_")
					       .append(elem[0])
					       .append("_")
					       .append(elem[1])
					       .append("_")
					       .append(elem[2]).toString();
		}
	},
	SECKILL_LOCK("seckill_lock") {
		/**
		 * 活动详情更新锁
		 *
		 * seckillId
		 *
		 * @param elem
		 * @return
		 */
		@Override
		public String getKey(Object... elem) {
			return new StringBuilder()
					       .append("seckill_lock")
					       .append("_")
					       .append(elem[0])
					       .toString();
		}
	},
	SECKILL_KEY("seckill") {
		/**
		 * 活动详情
		 *
		 * bizCode_sellerId_seckillId
		 *
		 * @param elem
		 * @return
		 */
		@Override
		public String getKey(Object... elem) {
			return new StringBuilder()
					       .append("seckill")
					       .append("_")
					       .append(elem[0])
					       .append("_")
					       .append(elem[1])
					       .append("_")
					       .append(elem[2]).toString();
		}
	},
	SECKILL_COUNT_LOCK("seckill_count_lock") {
		/**
		 * 活动参与人数 lock
		 *
		 * seckillId
		 *
		 * @param elem
		 * @return
		 */
		@Override
		public String getKey(Object... elem) {
			return new StringBuilder()
					       .append("seckill_count_lock")
					       .append("_")
					       .append(elem[0]).toString();
		}
	},
	SECKILL_COUNT("seckill_count") {
		/**
		 * 参与活动的数量
		 * bizCode_sellerId_seckillId
		 *
		 * @param elem
		 * @return
		 */
		@Override
		public String getKey(Object... elem) {
			return new StringBuilder()
					       .append("seckill_count")
					       .append("_")
					       .append(elem[0])
					       .append("_")
					       .append(elem[1])
					       .append("_")
					       .append(elem[2]).toString();
		}
	},
	USER_KEY("user") {
		/**
		 * 用户参与记录,每个用户对应活动的参与状态
		 *
		 * bizCode_sellerId_seckillId_userId
		 *
		 * @param elem
		 * @return
		 */
		@Override
		public String getKey(Object... elem) {
			return new StringBuilder()
					       .append("user")
					       .append("_")
					       .append(elem[0])
					       .append("_")
					       .append(elem[1])
					       .append("_")
					       .append(elem[2])
					       .append("_")
					       .append(elem[3]).toString();
		}
	},
	DISTRIBUTOR_KEY("distributor") {
		/**
		 * 分销商信息
		 * distributorId
		 * @param elem
		 * @return
		 */
		@Override
		public String getKey(Object... elem) {
			return new StringBuilder()
					       .append("distributor_id")
					       .append("_")
					       .append(elem[0]).toString();
		}
	},
	APPLY_SECKILL_LOCK("apply_seckill_lock") {
		/**
		 * 执行秒杀锁
		 *
		 * seckillId
		 *
		 * @param elem
		 * @return
		 */
		@Override
		public String getKey(Object... elem) {
			return new StringBuilder()
					       .append("apply_seckill_lock")
					       .append("_")
					       .append(elem[0]).toString();
		}
	};

	private String value;

	CacheKeyEnum(String value) {
		this.value = value;
	}

	public abstract String getKey(Object... elem);

	public String getValue() {
		return value;
	}
}