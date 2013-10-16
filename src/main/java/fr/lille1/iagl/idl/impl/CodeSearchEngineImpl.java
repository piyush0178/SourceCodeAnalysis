package fr.lille1.iagl.idl.impl;

import java.util.List;

import fr.lille1.iagl.idl.CodeSearchEngine;

public abstract class CodeSearchEngineImpl<T> implements CodeSearchEngine<T> {

	protected class LocationImpl implements Location {

		@Override
		public String getFilePath() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLineNumber() {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	protected class TypeImpl implements Type {

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getFullyQualifiedPackageName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public fr.lille1.iagl.idl.CodeSearchEngine.TypeKind getKind() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public fr.lille1.iagl.idl.CodeSearchEngine.Location getDeclaration() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	protected class MemberImpl implements Member {

		@Override
		public fr.lille1.iagl.idl.CodeSearchEngine.Type getType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	protected class FieldImpl implements Field {

		@Override
		public fr.lille1.iagl.idl.CodeSearchEngine.Type getType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	protected class MethodImpl implements Method {

		@Override
		public fr.lille1.iagl.idl.CodeSearchEngine.Type getType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<fr.lille1.iagl.idl.CodeSearchEngine.Type> getParamaters() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
